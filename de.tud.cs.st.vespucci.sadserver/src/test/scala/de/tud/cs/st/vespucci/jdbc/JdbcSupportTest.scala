package de.tud.cs.st.vespucci.jdbc

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterAll
import java.sql._

import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class SDBCTest extends FlatSpec
  with ShouldMatchers
  with BeforeAndAfterAll
  with JdbcSupport {

  import org.h2.jdbcx.JdbcConnectionPool
  val cp = JdbcConnectionPool.create("jdbc:h2:mem:jdbcsupport;DB_CLOSE_DELAY=-1", "admin", "admin")
  override def connection = cp.getConnection()

  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting JdbcSupportTests")
  }

  "SDBC" should "create some table" in {
    withSession {
      conn =>
        conn.executeStatement("SET COMPRESS_LOB DEFLATE")
        conn.executeStatement("""
            create table users (
            username varchar(100), 
            password varchar(50)
            )""")
        conn.executeStatement("""
            create table data (
            id varchar(100) NOT NULL,
            text BLOB
            )""")
    }
  }

  it should "insert entries" in {
    withPreparedStatement("insert into users values(?, ?)") {
      ps =>
        ps.executeUpdateWith("foo", "bar")
        ps.executeUpdateWith("quz", "baz")
    }
  }

  it should "find entries" in {
    withPreparedStatement("select top 1 password from users where username = ?") {
      _.executeQueryWith("quz").nextValue(varchar)
    } should equal { Some("baz") }
  }

  it should "delete" in {
    withPreparedStatement("delete from users where username = ?") {
      _.executeUpdateWith("quz")
    }
  }

  it should "not find deleted entries" in {
    withPreparedStatement("select * from users where username = ?") {
      _.executeQueryWith("quz").nextTuple(varchar)
    } should equal { None }
  }

  it should "update entries" in {
    withPreparedStatement("update users set password = ? where username = ?") {
      _.executeUpdateWith("updated", "foo")
    }
  }

  it should "find updated entries" in {
    withPreparedStatement("select * from users where username = ?") {
      _.executeQueryWith("foo").nextTuple(varchar, varchar)
    } should equal { Some("foo", "updated") }
  }

  it should "allow adding Null-entries" in {
    withPreparedStatement("insert into users values(?, ?)") {
      _.executeUpdateWith("mrNullPwd", Null.blob)
    }
  }

  it should "store text files as BLOB" in {
    withPreparedStatement("insert into data values(1, NULL)") { ps =>
      {
        import java.io._
        val is = new FileInputStream("/Users/mateusz/Desktop/das_bild_der_tu_darmstadt.pdf")
        val bs = new BufferedInputStream(is)
        ps.executeUpdate
      }
    }
  }

  it should "get files from db" in {

    withPreparedStatement("select text, length(text) from data where id = ?") { ps =>
      println(ps.executeQueryWith("1").nextTuple(binaryStream, int))
    }

  }

  override def afterAll() {
    withSession {
      conn =>
        conn.executeStatement("DROP ALL OBJECTS")
    }
  }

}