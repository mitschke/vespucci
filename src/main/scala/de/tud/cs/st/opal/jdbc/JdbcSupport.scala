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

  implicit def preparedStatement2RichPreparedStatement(ps: PreparedStatement): RichPreparedStatement = new RichPreparedStatement(ps)
  class RichPreparedStatement(ps: PreparedStatement) {
    def executeQueryWith(args: PreparedStatementArg*) = { var i = 1; for (arg <- args) { arg.prepare(i, ps); i = i + 1 }; ps.executeQuery }
    def executeUpdateWith(args: PreparedStatementArg*) = { var i = 1; for (arg <- args) { arg.prepare(i, ps); i = i + 1 }; ps.executeUpdate; ps }
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
  case class int(x: Int) extends PreparedStatementArg { def prepare(i: Int, p: PreparedStatement) { p.setInt(i, x) } }
  case class string(x: String) extends PreparedStatementArg { def prepare(i: Int, p: PreparedStatement) { p.setString(i, x) } }

  // ResultSet-accessors
  def string(column: String) = (rs: ResultSet) => rs.getString(column)
  def int(column: String) = (rs: ResultSet) => rs.getInt(column)

  def withinSession[T](session: Connection => T): T = {
    val conn = connection
    try {
      session(conn)
    } finally {
      conn close
    }
  }

  //  def int = ((n: Int) => ((rs: ResultSet) => (rs.getInt(n))))
  //  def int(x: Int) = ((n: Int) => ((ps: PreparedStatement) => (ps.setInt(n, x))))
  //  def string = ((n: Int) => ((rs: ResultSet) => (rs.getString(n))))

}

