package de.tud.cs.st.opal.vads
// Import the session management, including the implicit threadLocalSession
import org.scalaquery.session._
import org.scalaquery.session.Database.threadLocalSession
import org.scalaquery.ql.SimpleFunction

// Import the query language
import org.scalaquery.ql._

// Import the standard SQL types
import org.scalaquery.ql.TypeMapper._

// Use H2Driver which implements ExtendedProfile and thus requires ExtendedTables
import org.scalaquery.ql.extended.H2Driver.Implicit._
import org.scalaquery.ql.extended.{ ExtendedTable => Table }

/**
 * A simple example that uses statically typed queries against an in-memory
 * H2 database. The example data comes from Oracle's JDBC tutorial at
 * http://download.oracle.com/javase/tutorial/jdbc/basics/tables.html.
 */



object FirstExample {
  def main(args: Array[String]) {
    
    val User = new Table[(Long, String, String)]("USERS") {
        def id = column[Long]("ID", O.PrimaryKey)
        def name = column[String]("NAME")
        def surname = column[String]("SURNAME")
        def * = id ~ name ~ surname
      }

    val db = Database.forURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
//    		val db = Database.forURL("jdbc:h2:~/dbtest1", driver = "org.h2.Driver")
    db withSession{
      
      lazy val scopeIdentity = SimpleFunction.nullary[Long]("scope_identity")
      val lastId = (() => Query(SimpleFunction.nullary[Long]("scope_identity")).first)
      User.ddl.create
      User insert (0L, "foo", "bar")
      println("Inserted id " + lastId())
      User.insert(1L, "foo", "bar")
      println("Inserted id " + lastId())

    }
    
    db withSession {
      
      lazy val scopeIdentity = SimpleFunction.nullary[Long]("scope_identity")
      val lastId = (() => Query(SimpleFunction.nullary[Long]("scope_identity")).first)
      
      User insert (3L, "foo", "bar")
      println("Inserted id " + lastId())
      User.insert(5L, "foo", "bar")
      println("Inserted id " + lastId())

    }
    
    db withSession {
      
      lazy val scopeIdentity = SimpleFunction.nullary[Long]("scope_identity")
      val lastId = (() => Query(SimpleFunction.nullary[Long]("scope_identity")).first)
      
      User insert (7L, "foo", "bar")
      println("Inserted id " + lastId())
      User.insert(8L, "foo", "bar")
      println("Inserted id " + lastId())
      
       println(Query(User).list())

    }
  }
}
