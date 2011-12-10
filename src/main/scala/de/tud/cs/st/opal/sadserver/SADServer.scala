package de.tud.cs.st.opal.sadserver
import org.dorest.server.jdk.Server
import org.dorest.server.HandlerFactory
import org.scalaquery.session.Database._
import org.scalaquery.ql.extended.H2Driver.Implicit._
import com.weiglewilczek.slf4s.Logging
import org.dorest.server.rest._
import org.dorest.server.MediaType

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

    this register new HandlerFactory[SADdata] {
//      path { root :: "sads/" :: StringValue((desc, id) => desc.id = id) :: "/data" }
      path { root :: "data" }
      def create = new SADdata
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

  import scala.xml.XML.load
  import scala.io.Source.fromInputStream

  var id: String = _

  implicit def clob2xml(x: java.sql.Clob) = load(fromInputStream(x.getAsciiStream).toString)

  get returns TEXT {
    db withSession {
      val query = for { sad <- sads if sad.id === id } yield sad.name
      None
    }
  }

  get returns XML {
    db withSession {
      val query = for { sad <- sads if sad.id === id } yield sad.data
      query.firstOption() match {
        // 1st option: does entry exist in DB? 2nd option: has entry sad-clob?
        case Some(Some(clob)) => Some(clob2xml(clob))
        case _ => None
      }
    }
  }

}

class SADdata extends RESTInterface with DatabaseAccess with StreamSupport {

  var id: String = _

  import scala.xml.XML.load
  import scala.io.Source.fromInputStream
  val is = new java.io.FileInputStream("/Users/mateusz/Desktop/das_bild_der_tu_darmstadt.pdf")
  val bs = new java.io.BufferedInputStream(is)
  get returns Binary(MediaType.APPLICATION_PDF) {
	(bs, 2975904)
  }
  
  

  //  get returns PDF {
  //    db withSession {
  //      val query = for { sad <- sads if sad.id === id } yield sad.data
  //      query.firstOption() match {
  //        // 1st option: does entry exist in DB? 2nd option: has entry sad-clob?
  //        case Some(Some(clob)) => Some(clob2xml(clob))
  //        case _ => None
  //      }
  //    }
  //  }

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
      <sads>{ query.list map (e => <sad id={ e._1 } name={ e._2 }/>) }</sads>
    }
  }

  var id: String = _
  post of XML returns XML {
    val sad = SADParser(XMLRequestBody)
    db withSession {
      id = uniqueId
      logger.info("Persisting " + sad + " with id=" + id)
      sads insert (id, sad.diagramName, sad.xmlData, None, None, false)
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
object SADServerApp extends scala.App with Logging {

  logger.info("Starting Software Architecture Description Server...")

  val configuration = new scala.sys.SystemProperties()
  configuration += ("org.tud.cs.st.opal.sadserver.database" -> "jdbc:h2:sads")

  SADServer

}
