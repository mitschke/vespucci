package de.tud.cs.st.opal.jdbc

import java.sql.{ Connection, PreparedStatement, ResultSet, Types }

import scala.collection.mutable.MutableList

trait JdbcSupport {
  
   def uuid = java.util.UUID.randomUUID().toString

  /**
   * Mixins must provide a java.sql.connection.
   */
  protected def connection: Connection

  implicit def connection2RichConnection(conn: Connection): RichConnection = new RichConnection(conn)
  class RichConnection(conn: Connection) {
    def executeQuery(sql: String): ResultSet = { val stmnt = conn.createStatement; stmnt.executeQuery(sql) }
    def executeUpdate(sql: String): Int = { val stmnt = conn.createStatement; val result = stmnt.executeUpdate(sql); stmnt.close; result }
    def executeStatement(sql: String): Boolean = { val stmnt = conn.createStatement; val result = stmnt.execute(sql); stmnt.close; result }
  }

  object Null {
    import Types._
    def varchar = SqlNullType(VARCHAR)
    def integer = SqlNullType(INTEGER)
    def blob = SqlNullType(BLOB)

  }
  final private[JdbcSupport] case class SqlNullType(typeNr: Int)

  implicit def preparedStatement2RichPreparedStatement(ps: PreparedStatement): RichPreparedStatement = new RichPreparedStatement(ps)
  class RichPreparedStatement(ps: PreparedStatement) {
    def executeQueryWith(args: Any*) = { var i = 0; for (arg <- args) { i = i + 1; prep(arg, i) }; ps.executeQuery }
    def executeUpdateWith(args: Any*) { var i = 0; for (arg <- args) { i = i + 1; prep(arg, i) }; ps.executeUpdate }
    def executeWith(args: Any*) { var i = 0; for (arg <- args) { i = i + 1; prep(arg, i) }; ps.execute }

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
        case SqlNullType(typeNr) => ps.setNull(i, typeNr)
        case _ => throw new RuntimeException("Argument type not known")
      }
    }
  }

  implicit def resultSet2RichResultSet(rs: ResultSet): RichResultSet = new RichResultSet(rs)
  class RichResultSet(rs: ResultSet) {

    import scala.collection.mutable.MutableList

    def nextValue[T1](a1: ((ResultSet, Int) => T1)) = { if (rs.next) Some(a1(rs, 1)) else None }

    def listValues[A](a: ((ResultSet, Int) => A)) = { val list = MutableList[A](); while (rs.next) { list.+=(a(rs, 1)) }; list }
    def listTuples[A](a: ((ResultSet, Int) => A)) = { val list = MutableList[Tuple1[A]](); while (rs.next) { list += Tuple1(a(rs, 1)) }; list }
    def listTuples[A, B](a: ((ResultSet, Int) => A), b: ((ResultSet, Int) => B)) = { val list = MutableList[Tuple2[A, B]](); while (rs.next) { list += Tuple2(a(rs, 1), b(rs, 2)) }; list }
    def listTuples[A, B, C](a: ((ResultSet, Int) => A), b: ((ResultSet, Int) => B), c: ((ResultSet, Int) => C)) = { val list = MutableList[Tuple3[A, B, C]](); while (rs.next) { list += Tuple3(a(rs, 1), b(rs, 2), c(rs, 3)) }; list }
    def nextTuple[T1](a1: ((ResultSet, Int) => T1)) = { if (rs.next) Some(Tuple(a1(rs, 1))) else None }
    def nextTuple[T1, T2](a1: ((ResultSet, Int) => T1), a2: ((ResultSet, Int) => T2)) = { if (rs.next) Some(Tuple(a1(rs, 1), a2(rs, 2))) else None }
    def nextTuple[T1, T2, T3](a1: ((ResultSet, Int) => T1), a2: ((ResultSet, Int) => T2), a3: ((ResultSet, Int) => T3)) = { if (rs.next) Some(Tuple(a1(rs, 1), a2(rs, 2), a3(rs, 3))) else None }
    def nextTuple[T1, T2, T3, T4](a1: ((ResultSet, Int) => T1), a2: ((ResultSet, Int) => T2), a3: ((ResultSet, Int) => T3), a4: ((ResultSet, Int) => T4)) = { if (rs.next) Some(Tuple(a1(rs, 1), a2(rs, 2), a3(rs, 3), a4(rs, 4))) else None }
    def nextTuple[T1, T2, T3, T4, T5](a1: ((ResultSet, Int) => T1), a2: ((ResultSet, Int) => T2), a3: ((ResultSet, Int) => T3), a4: ((ResultSet, Int) => T4), a5: ((ResultSet, Int) => T5)) = { if (rs.next) Some(Tuple(a1(rs, 1), a2(rs, 2), a3(rs, 3), a4(rs, 4), a5(rs, 5))) else None }
    def nextTuple[T1, T2, T3, T4, T5, T6](a1: ((ResultSet, Int) => T1), a2: ((ResultSet, Int) => T2), a3: ((ResultSet, Int) => T3), a4: ((ResultSet, Int) => T4), a5: ((ResultSet, Int) => T5), a6: ((ResultSet, Int) => T6)) = { if (rs.next) Some(Tuple(a1(rs, 1), a2(rs, 2), a3(rs, 3), a4(rs, 4), a5(rs, 5), a6(rs, 6))) else None }
    def nextTuple[T1, T2, T3, T4, T5, T6, T7](a1: ((ResultSet, Int) => T1), a2: ((ResultSet, Int) => T2), a3: ((ResultSet, Int) => T3), a4: ((ResultSet, Int) => T4), a5: ((ResultSet, Int) => T5), a6: ((ResultSet, Int) => T6), a7: ((ResultSet, Int) => T7)) = { if (rs.next) Some(Tuple(a1(rs, 1), a2(rs, 2), a3(rs, 3), a4(rs, 4), a5(rs, 5), a6(rs, 6), a7(rs, 7))) else None }

    def isEmpty() = { !rs.next }
  }

  // ResultSet-accessors
  def string = ((rs: ResultSet, i: Int) => rs.getString(i))
  def varchar = string
  def int = ((rs: ResultSet, i: Int) => rs.getInt(i))
  def characterStream = ((rs: ResultSet, i: Int) => rs.getCharacterStream(i))
  def clob = characterStream
  def binaryStream = ((rs: ResultSet, i: Int) => rs.getBinaryStream(i))
  def blob = binaryStream
  def boolean = ((rs: ResultSet, i: Int) => rs.getBoolean(i))

  def withSession[T](session: Connection => T): T = {
    val conn = connection
    try {
      session(conn)
    } finally {
      conn close
    }
  }

  /**
   * Creates a Connection and loans it to the given function which may return any type.
   */
  def withTransaction[T](session: Connection => T): T = {
    val conn = connection
    conn.setAutoCommit(false)
    try {
      val result = session(conn)
      conn.commit()
      result
    } catch {
      case ex => {
        conn.rollback()
        throw ex
      }
    } finally {
      conn close
    }
  }

  /**
   * Creates a PreparedStatement and loans it to the given function which may return any type.
   * Closes all resources after function application. According to the  java.sql.Statement-doc
   * when the "Statement object is closed, its current ResultSet object,
   * if one exists, is also closed."
   */
  def withPreparedStatement[T](sql: String)(session: PreparedStatement => T): T = {
    val conn = connection
    conn.setAutoCommit(true)
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

  def withQuery[T](sql: String)(session: ResultSet => T): T = {
    val conn = connection
    conn.setAutoCommit(true)
    val stmnt = conn.createStatement
    val rs = stmnt.executeQuery(sql)
    try {
      session(rs)
    } finally {
      if (rs != null)
        rs.close
      if (stmnt != null)
        stmnt.close
      if (conn != null)
        conn.close
    }
  }

  def withUpdate[T](sql: String)(session: Int => T): T = {
    val conn = connection
    conn.setAutoCommit(true)
    val stmnt = conn.createStatement
    val result = stmnt.executeUpdate(sql)
    try {
      session(result)
    } finally {
      if (stmnt != null)
        stmnt.close
      if (conn != null)
        conn.close
    }
  }

  def withExecution[T](sql: String)(session: Boolean => T): T = {
    val conn = connection
    conn.setAutoCommit(true)
    val stmnt = conn.createStatement
    val result = stmnt.execute(sql)
    try {
      session(result)
    } finally {
      if (stmnt != null)
        stmnt.close
      if (conn != null)
        conn.close
    }
  }

}

