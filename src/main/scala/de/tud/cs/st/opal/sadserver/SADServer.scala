package de.tud.cs.st.opal.sadserver
import org.dorest.server.jdk.Server
import org.dorest.server.HandlerFactory
import org.scalaquery.session.Database._
import org.scalaquery.ql.extended.H2Driver.Implicit._
import com.weiglewilczek.slf4s.Logging

/**
 * Software Architecture Description Server
 */
object SADServer
  extends Server(9000)
  with DatabaseAccess
  with Logging {

  startDatabase()

  def root = "/"

  this register new HandlerFactory[Root] {
    path { root }
    def create = new Root
  }

  this register new HandlerFactory[SADs] {
    path { root :: "sads" }

    def create = new SADs
  }

  this register new HandlerFactory[SAD] {
    path { root :: "sads/" :: StringValue((desc, id) => desc.id = id) }
    def create = new SAD
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

class SAD extends RESTInterface with DatabaseAccess with TEXTSupport with XMLSupport {

  var id: String = _

  get returns TEXT {
    db withSession {
      val query = for { sad <- sads if sad.id === id } yield sad.name
      query.list mkString "\n"
    }
  }

  get returns XML {
    db withSession {
      val query = for { sad <- sads if sad.id === id } yield sad.description
      val l: List[String] = query.list
      val result: scala.xml.Elem = scala.xml.XML.loadString(l.first)
      val r: Option[scala.xml.Node] = if (l.isEmpty) None else result
      r
      if (l.isEmpty) None else result
    }
  }

}

class SADs extends RESTInterface with DatabaseAccess with TEXTSupport with XMLSupport {

  get returns TEXT {
    db withSession {
      val query = for { sad <- sads } yield sad.id ~ sad.name
      query.list mkString "\n"
    }
  }

  get returns XML {
    db withSession {
      val query = for { sad <- sads } yield sad.id ~ sad.name
      <sads>{ query.list.map(e => <sad id={ e._1 } name={ e._2 }/>) }</sads>
    }
  }

  var id: String = _
  post of XML returns XML {
    val sad = SADParser(XMLRequestBody)
    db withSession {
      id = uniqueId
      logger.info("Persisting " + sad + " with id=" + id)
      sads insert (id, sad.diagramName, sad.xmlData)
    }
    <success><id>{ id }</id></success>
  }

}

class Users extends RESTInterface with RegisteredUserAuthorization with TEXTSupport with XMLSupport {

  get returns TEXT { "Hello " + username + "!" }

  get returns XML { <hello>{ username }</hello> }

}

/**
 * Starts the SADServer as a configured stand-alone application.
 */
object VADServerApp extends scala.App with Logging {

  logger.info("Starting Software Architecture Description Server...")

  val configuration = new scala.sys.SystemProperties()
  configuration += ("org.tud.cs.st.opal.sadserver.database" -> "jdbc:h2:sads")

  SADServer

}
