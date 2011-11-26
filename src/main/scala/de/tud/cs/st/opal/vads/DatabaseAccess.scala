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

import com.weiglewilczek.slf4s.Logging

trait DatabaseAccess extends Logging {

  val props = new scala.sys.SystemProperties()
  val db = Database.forURL(props("org.tud.cs.st.opal.vads.database"), driver = "org.h2.Driver")

  import org.scalaquery.session.Database._

  def uniqueId: String = java.util.UUID.randomUUID().toString()

  object descriptions extends Table[(String, String, String)]("DESCRIPTIONS") {
    def id = column[String]("ID", O.PrimaryKey)
    def name = column[String]("NAME")
    def description = column[String]("XMLDATA")
    def * = id ~ name ~ description
  }

  def startDatabase() = {
    db withSession {
      if (isDatabaseEmpty) {
        logger.info("Creating database tables...")
        (descriptions).ddl create
      }
      logger.info("Database startup completed.")
    }
  }

  protected def isDatabaseEmpty: Boolean = org.scalaquery.meta.MTable.getTables.list().length == 0
}


