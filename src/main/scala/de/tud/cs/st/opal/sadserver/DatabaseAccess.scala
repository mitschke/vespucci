package de.tud.cs.st.opal.sadserver

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
  val db = Database.forURL(props("org.tud.cs.st.opal.sadserver.database"), driver = "org.h2.Driver")

  import org.scalaquery.session.Database._

  def uniqueId: String = java.util.UUID.randomUUID().toString()

  final class QueryAssoc[A](val query: org.scalaquery.ql.Query[org.scalaquery.ql.NamedColumn[A]]) {
    @inline def >>>[B](f: (A => B)): Option[B] = {
      val l = query.list
      val result = f(l.first)
      val r: Option[B] = if (l.isEmpty) None else Option(result)
      r
    }
  }
  implicit def any2QueryAssoc[A](x: org.scalaquery.ql.Query[org.scalaquery.ql.NamedColumn[A]]): QueryAssoc[A] = new QueryAssoc(x)

  /**
   * Stores software architecture descriptions
   */
  object sads extends Table[(String, String, String)]("SADS") {
    def id = column[String]("ID", O.PrimaryKey)
    def name = column[String]("NAME")
    def description = column[String]("XMLDATA")
    def * = id ~ name ~ description
  }

  /**
   * Stores registered users in the system
   */
  object users extends Table[(String, String)]("USERS") {
    def username = column[String]("USERNAME", O.PrimaryKey)
    def password = column[String]("PASSWORD")
    def * = username ~ password
  }

  def startDatabase() = {
    db withSession {
      if (isDatabaseEmpty) {
        logger.info("Creating database tables...")
        (sads.ddl ++ users.ddl).create

        logger.info("Creating initial users...")
        users.insert("admin", "password")
        users.insert("mateusz", "password")
      }
      logger.info("Database startup completed.")
    }
  }

  protected def isDatabaseEmpty: Boolean = org.scalaquery.meta.MTable.getTables.list().length == 0
}


