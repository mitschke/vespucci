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
    withinSession { conn =>
      {
        conn prepareStatement ("insert into users values(?, ?)") executeUpdateWith (string("foo"), string("bar")) close;
        conn prepareStatement ("insert into users values(?, ?)") executeUpdateWith (string("quz"), string("baz")) close
      }
    }
  }

  it should "find entries" in {
    withinSession { conn =>
      {
        conn prepareStatement ("select top 1 * from users where username = ?") executeQueryWith (string("quz")) getFromNextRowAndClose (string("password"))
      }
    } should equal { Some(Tuple1("baz")) }
  }

  it should "delete" in {
    withinSession { conn =>
      {
        conn prepareStatement ("delete from users where username = ?") executeUpdateWith (string("quz")) close
      }
    }
  }

  it should "not find deleted entries" in {
    withinSession { conn =>
      {
        conn prepareStatement ("select * from users where username = ?") executeQueryWith (string("quz")) getFromNextRowAndClose (string("password"))
      }
    } should equal { None }
  }

  it should "update entries" in {
    withinSession { conn =>
      {
        conn prepareStatement ("update users set password = ? where username = ?") executeUpdateWith (string("updated"), string("foo")) close
      }
    }
  }

  it should "find updated entries" in {
    withinSession { conn =>
      {
        conn prepareStatement ("select top 1 * from users where username = ?") executeQueryWith (string("foo")) getFromNextRowAndClose (string("password"))
      }
    } should equal { Some(Tuple1("updated")) }
  }

}