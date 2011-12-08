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

  it should "store text files as CLOB" in {
    withPreparedStatement("insert into data values(?, ?)") { ps =>
      {
        import java.io._
        //        ps.executeUpdateWith("1", new java.io.FileReader("/Users/mateusz/Desktop/ideaIC-10.5.2.dmg"))
        val is = new java.io.FileInputStream("/Users/mateusz/Desktop/das_bild_der_tu_darmstadt.pdf")
        val bs = new BufferedInputStream(is)
        ps.executeUpdateWith("1", bs)
      }
    }
  }

  it should "get files from db" in {

    withPreparedStatement("select text, length(text) from data where id = ?") { ps =>
      println(ps.executeQueryWith("1").nextTuple(binaryStream, int))
    }

    withPreparedStatement("select text from data where id = ?") { ps =>
      {
        import java.io._
        import org.apache.commons.io.IOUtils
        val r: java.io.InputStream = ps.executeQueryWith("1").nextValue(binaryStream).get
        //        val stream: java.io.InputStream = tuple.getOrElse(null)
        //        print(new java.util.Scanner(stream).useDelimiter("\\A").next)
        //        val o: FileWriter = new FileWriter("ideaIC-10.5.2.dmg")
        val outputStream = new BufferedOutputStream(new FileOutputStream("das_bild_der_tu_darmstadt.pdf"), 1024)
        val o: FileWriter = new FileWriter("ideaIC-10.5.2.dmg")
        //        send(r, o)
        IOUtils.copy(r, outputStream)

        def send(inputStream: Reader, outputStream: FileWriter) {
          try {
            var c: Int = 0
            while (c != -1) {
              c = inputStream.read()
              outputStream.write(c);
            }
          } finally {
            if (inputStream != null) {
              inputStream.close();
            }
            if (outputStream != null) {
              outputStream.close();
            }
          }
        }

        def sendStream(in: InputStream, out: OutputStream) {
          try {
            println("Sending")
            val buffer = new scala.Array[Byte](1024) // Adjust if you want
            var bytesRead: Int = 0
            while (bytesRead != -1) {
              bytesRead = in.read(buffer)
              out.write(buffer, 0, bytesRead)
            }

          } finally {
            in.close()
            out.close()
          }
        }

      }
    }
  }

}