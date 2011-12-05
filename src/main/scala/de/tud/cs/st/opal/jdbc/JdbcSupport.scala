package de.tud.cs.st.opal.jdbc

import java.sql._

trait JdbcSupport {

  /**
   * Mixins must provide a java.sql.connection.
   */
  protected def connection: Connection

  implicit def connection2RichConnection(conn: Connection): RichConnection = new RichConnection(conn)
  class RichConnection(conn: Connection) {
    def prepare(stmt: String) = conn.prepareStatement(stmt)
  }

  case class Null(any: Any)
  case class Null_String
  case class Null_Int
  case class Null_Boolean
  case class Null_Float
  case class Null_Double
  case class Null_Blob
  case class Null_Clob
  
  implicit def preparedStatement2RichPreparedStatement(ps: PreparedStatement): RichPreparedStatement = new RichPreparedStatement(ps)
  class RichPreparedStatement(ps: PreparedStatement) {
    def executeQueryWith(args: Any*) = { var i = 0; for (arg <- args) { i = i + 1; prep(arg, i) }; ps.executeQuery }
    def executeUpdateWith(args: Any*) { var i = 0; for (arg <- args) { i = i + 1; prep(arg, i) }; ps.executeUpdate }

    private[this] def prep[T](arg: T, i: Int) {
      import java.sql.Types
      arg match {
        case any: String => ps.setString(i, any)
        case any: Int => ps.setInt(i, any)
        case any: Boolean => ps.setBoolean(i, any)
        case any: Double => ps.setDouble(i, any)
        case any: Float => ps.setFloat(i, any)
        case any: java.io.InputStream => ps.setBinaryStream(i, any)
        case any: java.io.Reader => ps.setCharacterStream(i, any)
        case Null_String => ps.setNull(i, Types.VARCHAR)
        case Null_Int => ps.setNull(i, Types.INTEGER)
        case Null_Boolean => ps.setNull(i, Types.BOOLEAN)
        case Null_Float => ps.setNull(i, Types.FLOAT)
        case Null_Double => ps.setNull(i, Types.DOUBLE)
        case Null_Blob => ps.setNull(i, Types.BLOB)
        case Null_Clob => ps.setNull(i, Types.CLOB)
        case _ => throw new RuntimeException("Argument type not known")
      }
    }
  }

  implicit def resultSet2RichResultSet(rs: ResultSet): RichResultSet = new RichResultSet(rs)
  class RichResultSet(rs: ResultSet) {

    def getFromNextRow[T1](f1: (ResultSet => T1)) = if (rs.next) Some(Tuple(f1(rs))) else None
    def getFromNextRow[T1, T2](f1: (ResultSet => T1), f2: (ResultSet => T2)) = if (rs.next) Some(Tuple(f1(rs), f2(rs))) else None
    def getFromNextRow[T1, T2, T3](f1: (ResultSet => T1), f2: (ResultSet => T2), f3: (ResultSet => T3)) = if (rs.next) Some(Tuple(f1(rs), f2(rs), f3(rs))) else None
    def getFromNextRow[T1, T2, T3, T4](f1: (ResultSet => T1), f2: (ResultSet => T2), f3: (ResultSet => T3), f4: (ResultSet => T4)) = if (rs.next) Some(Tuple(f1(rs), f2(rs), f3(rs), f4(rs))) else None

    def isEmpty() = { val result = rs.next; rs.close; result }
    def getFromNextRowAndClose[T1](f1: (ResultSet => T1)) = { val result = if (rs.next) Some(Tuple(f1(rs))) else None; rs.close; result }
    def getFromNextRowAndClose[T1, T2](f1: (ResultSet => T1), f2: (ResultSet => T2)) = { val result = if (rs.next) Some(Tuple(f1(rs), f2(rs))) else None; rs.close; result }
    def getFromNextRowAndClose[T1, T2, T3](f1: (ResultSet => T1), f2: (ResultSet => T2), f3: (ResultSet => T3)) = { val result = if (rs.next) Some(Tuple(f1(rs), f2(rs), f3(rs))) else None; rs.close; result }
    def getFromNextRowAndClose[T1, T2, T3, T4](f1: (ResultSet => T1), f2: (ResultSet => T2), f3: (ResultSet => T3), f4: (ResultSet => T4)) = { val result = if (rs.next) Some(Tuple(f1(rs), f2(rs), f3(rs), f4(rs))) else None; rs.close; result }
  }

  // PreparedStatement-Arguments
  abstract case class PreparedStatementArg { def prepare(i: Int, p: PreparedStatement): Unit }
  case class string(x: String) extends PreparedStatementArg { def prepare(i: Int, p: PreparedStatement) { p.setString(i, x) } }
  case class int(x: Int) extends PreparedStatementArg { def prepare(i: Int, p: PreparedStatement) { p.setInt(i, x) } }
  case class characterStream(x: java.io.Reader) extends PreparedStatementArg { def prepare(i: Int, p: PreparedStatement) { p.setCharacterStream(i, x) } }
  case class binaryStream(x: java.io.InputStream) extends PreparedStatementArg { def prepare(i: Int, p: PreparedStatement) { p.setBinaryStream(i, x) } }

  // ResultSet-accessors
  def string(column: String) = (rs: ResultSet) => rs.getString(column)
  def int(column: String) = (rs: ResultSet) => rs.getInt(column)
  def characterStream(column: String) = (rs: ResultSet) => rs.getCharacterStream(column)
  def binaryStream(column: String) = (rs: ResultSet) => rs.getBinaryStream(column)
  def boolean(column: String) = (rs: ResultSet) => rs.getBoolean(column)

  def withinSession[T](session: Connection => T): T = {
    val conn = connection
    try {
      session(conn)
    } finally {
      conn close
    }
  }

  def withinPreparedStatement[T](sql: String)(session: PreparedStatement => T): T = {
    val conn = connection
    val ps = conn.prepareStatement(sql)
    try {
      session(ps)
    } finally {
      if (ps != null)
        ps.close
      if (conn != null)
        conn.close
    }
  }

  //  def int = ((n: Int) => ((rs: ResultSet) => (rs.getInt(n))))
  //  def int(x: Int) = ((n: Int) => ((ps: PreparedStatement) => (ps.setInt(n, x))))
  //  def string = ((n: Int) => ((rs: ResultSet) => (rs.getString(n))))

}

