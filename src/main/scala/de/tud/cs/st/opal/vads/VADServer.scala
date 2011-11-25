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

  def root = "/"

  this register new HandlerFactory[Root] {
    path { root }
    def create = new Root
  }

  this register new HandlerFactory[Descriptions] {
    path { root :: "descriptions" }
    db withSession {
      descriptions.ddl.create
    }
    implicit def create = new Descriptions
  }

  this register new HandlerFactory[Description] {
    path { root :: "descriptions/" :: StringValue((desc, id) => desc.id = id) }
    implicit def create = new Description()
  }

  start()
}

import org.dorest.server.rest._

class Root extends RESTInterface with TEXTSupport {

  get returns TEXT { "Hello!" }

}

class Description extends RESTInterface with DatabaseAccess with TEXTSupport with XMLSupport {

  var id: String = _
  get returns TEXT {
    db withSession {
     val query = for { ad <- descriptions if ad.id === id } yield ad.name
     query.list mkString "\n"
    }
  }

}

class Descriptions extends RESTInterface with DatabaseAccess with TEXTSupport with XMLSupport {

  get returns TEXT {
    db withSession {
     val query = for { ad <- descriptions } yield ad.name
     query.list mkString "\n"
    }
  }

  var id: String = _
  post of XML returns XML {
    db withSession {
      id = uniqueId()
      descriptions insert (id, id, "bar")
    }
    <success><id>{id}</id></success>
  }

}

class VADServerApp extends VADServer with scala.App
