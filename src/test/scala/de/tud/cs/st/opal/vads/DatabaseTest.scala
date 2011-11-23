package de.tud.cs.st.opal.vads

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import org.scalaquery.session._
import org.scalaquery.session.Database.threadLocalSession

// Import the query language
import org.scalaquery.ql._

// Import the standard SQL types
import org.scalaquery.ql.TypeMapper._

// Use H2Driver which implements ExtendedProfile and thus requires ExtendedTables
import org.scalaquery.ql.extended.H2Driver.Implicit._
import org.scalaquery.ql.extended.{ExtendedTable => Table}

@RunWith(classOf[JUnitRunner])
class DatabaseTest extends FlatSpec with ShouldMatchers {

  "The database" should "be initialized" in {
    
    val Tuples = new Table[(Int, String)]("SIMPLE") {
      def id = column[Int]("ID", O.PrimaryKey) // This is the primary key column
      def name = column[String]("NAME")
      def * = id ~ name
    }
    
      Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver") withSession {
        
        (Tuples.ddl).create
        
        Tuples.insert(1, "foo")
        Tuples.insert(2, "bar")
        
       val query = for(t <- Tuples if t.id === 2) yield t

      }
  }
  
}