package de.tud.cs.st.opal.vads

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

trait DatabaseAccess {
  
   val props = new scala.sys.SystemProperties()
  val db = Database.forURL(props("org.tud.cs.st.opal.vads.database"), driver = "org.h2.Driver")

  import org.scalaquery.session.Database._

  val uniqueId = (() => java.util.UUID.randomUUID().toString())

  object descriptions extends Table[(String, String, String)]("DESCRIPTIONS") {
    def id = column[String]("ID", O.PrimaryKey)
    def name = column[String]("NAME")
    def description = column[String]("DESCRIPTION")
    def * = id ~ name ~ description
  }

  

}


