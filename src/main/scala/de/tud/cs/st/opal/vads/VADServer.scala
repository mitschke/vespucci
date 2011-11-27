package de.tud.cs.st.opal.vads
import org.dorest.server.jdk.Server
import org.dorest.server.HandlerFactory
import org.scalaquery.session.Database._
import org.scalaquery.ql.extended.H2Driver.Implicit._
import com.weiglewilczek.slf4s.Logging

/**
 * Vespucci Architecture Description Server
 */

import org.scalaquery.session.Database
object VADServer
  extends Server(9000)
  with DatabaseAccess
  with Logging {

  startDatabase()

  def root = "/"

  this register new HandlerFactory[Root] {
    path { root }
    def create = new Root
  }

  this register new HandlerFactory[Sads] {
    path { root :: "sads" }

    def create = new Sads
  }

  this register new HandlerFactory[SadRessource] {
    path { root :: "sads/" :: StringValue((desc, id) => desc.id = id) }
    def create = new SadRessource
  }

  this register new HandlerFactory[Users] {
    path { root :: "users" }
    def create = new Users
  }

  start()
}

import org.dorest.server.rest._

class Root extends RESTInterface with TEXTSupport with HTMLSupport {

  get returns HTML { "Hello!" }

}

class SadRessource extends RESTInterface with DatabaseAccess with TEXTSupport with XMLSupport {

  var id: String = _

  get returns TEXT {
    db withSession {
      val query = for { sad <- SADS if sad.id === id } yield sad.name
      query.list mkString "\n"
    }
  }

  get returns XML {
    db withSession {
      val query = for { sad <- SADS if sad.id === id } yield sad.description
      scala.xml.XML.loadString(query.list first)
    }
  }

}

class Sads extends RESTInterface with DatabaseAccess with TEXTSupport with XMLSupport {

  get returns TEXT {
    db withSession {
      val query = for { sad <- SADS } yield sad.id ~ sad.name
      query.list mkString "\n"
    }
  }

  get returns XML {
    db withSession {
      val query = for { sad <- SADS } yield sad.id ~ sad.name
      <sads>{ query.list.map(e => <sad id={e._1} name={e._2}/>)}</sads>
    }
  }

  var id: String = _
  post of XML returns XML {
    val sad = SAD(XMLRequestBody)
    db withSession {
      id = uniqueId
      logger.info("Persisting " + sad + " with id=" + id)
      SADS insert (id, sad.diagramName, sad.xmlData)
    }
    <success><id>{id}</id></success>
  }

}

class Users extends RESTInterface with RegisteredUserAuthorization with TEXTSupport with XMLSupport {

  get returns TEXT { "Hello " + username + "!" }
  
  get returns XML { <hello>{username}</hello> }

}

object VADServerApp extends scala.App with Logging {

  logger.info("Starting Vespucci Architecture Description Server...")

  val configuration = new scala.sys.SystemProperties()
  configuration += ("org.tud.cs.st.opal.vads.database" -> "jdbc:h2:vads")

  VADServer

}
