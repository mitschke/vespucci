package de.tud.cs.st.opal.jdbc

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterAll
import java.sql._

import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class SDBCTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll with JdbcSupport with H2DatabaseConnection {

  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting tests")
  }

  def quer(implicit conn: Connection) {
    println(conn)
  }

  "SDBC" should "create some table" in {
    withinSession { conn =>
      {
        conn prepareStatement ("create table users (username varchar(100), password varchar(50))") execute
      }
    }
  }

  it should "insert entries" in {
    withinPreparedStatement("insert into users values(?, ?)") { ps =>
      {
        ps executeUpdateWith ("foo", "bar")
        ps executeUpdateWith ("quz", "baz")
      }
    }
  }

  it should "find entries" in {
    withinPreparedStatement("select top 1 password from users where username = ?") { ps =>
      {
        ps executeQueryWith ("quz") getFromNextRow (string("password"))
      }
    } should equal { Some(Tuple1("baz")) }
  }

  it should "delete" in {
    withinPreparedStatement("delete from users where username = ?") { ps =>
      {
        ps executeUpdateWith ("quz")
      }
    }
  }

  it should "not find deleted entries" in {
    withinPreparedStatement("select * from users where username = ?") { ps =>
      {
        ps executeQueryWith ("quz") getFromNextRow (string("password"))
      }
    } should equal { None }
  }

  it should "update entries" in {
    withinPreparedStatement("update users set password = ? where username = ?") { ps =>
      {
        ps executeUpdateWith ("updated", "foo")
      }
    }
  }

  it should "find updated entries" in {
    withinPreparedStatement("select password from users where username = ?") { ps =>
      {
        ps executeQueryWith ("foo") getFromNextRow (string("password"))
      }
    } should equal { Some(Tuple1("updated")) }
  }
  
   it should "allow adding Null-entries" in {
    withinPreparedStatement("insert into users values(?, ?)") { ps =>
      {
        ps executeUpdateWith ("mrNullPwd", Null_Int)
      }
    }
  }

}