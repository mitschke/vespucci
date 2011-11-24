package de.tud.cs.st.opal.vads
import org.dorest.server.jdk.Server
import org.dorest.server.HandlerFactory
import org.dorest.server.log.ConsoleLogging

import org.scalaquery.session.Database._
import org.scalaquery.ql.extended.H2Driver.Implicit._

/**
 * Vespucci Architecture Description Server
 */

import org.scalaquery.session.Database
class VADServer
  extends Server(9000)
  with DatabaseAccess
  with ConsoleLogging // TODO needs to exchanged
  {

  val props = new scala.sys.SystemProperties()
  val db = Database.forURL(props("org.tud.cs.st.opal.vads.database"), driver = "org.h2.Driver")

  def root = "/"

  this register new HandlerFactory[Root] {
    path { root }
    def create = new Root
  }

  this register new HandlerFactory[Descriptions] {
    path { root :: "descriptions" }
    db withSession {
      architectureDescriptions.ddl.create
    }
    implicit def create = new Descriptions(db)
  }

  this register new HandlerFactory[Description] {
    path { root :: "descriptions" :: LongValue((desc, longid) => desc.id = longid) }
    implicit def create = new Description()
  }

  start()
}

import org.dorest.server.rest._

class Root extends RESTInterface with TEXTSupport {

  get returns TEXT { "Hello!" }

}

class Description extends RESTInterface with TEXTSupport with XMLSupport {

  var id: Long = _

}

class Descriptions(db: Database) extends RESTInterface with DatabaseAccess with TEXTSupport with XMLSupport {

  get returns TEXT {
    db withSession {
      org.scalaquery.ql.Query(architectureDescriptions).list().toString()
    }
  }

  var id: String = _
  post of XML returns XML {
    db withSession {
      id = uniqueId()
      architectureDescriptions insert (id, "foo", "bar")
    }
    <success><id> { id } </id></success>
  }

}

class VADServerApp extends VADServer with scala.App
