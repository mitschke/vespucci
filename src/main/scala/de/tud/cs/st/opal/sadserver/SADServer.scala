package de.tud.cs.st.opal.sadserver
import org.dorest.server.jdk.Server
import org.dorest.server.HandlerFactory
import com.weiglewilczek.slf4s._
import org.dorest.server.rest._
import org.dorest.server.MediaType

/**
 * Software Architecture Description Server
 */
object SADServer
  extends Server(9000)
  with DAO
  with Logging {

  logger.debug("Starting Software Architecture Description Server...")

  startDatabase()

  val rootPath = ""
  val descriptionCollectionPath = "/sads"
  val userCollectionPath = "/users"

  this register new HandlerFactory[RootResource] {
    path { rootPath }
    def create = new RootResource
  }

  this register new HandlerFactory[DescriptionCollectionResource] {
    path { descriptionCollectionPath }
    def create = new DescriptionCollectionResource
  }

  this register new HandlerFactory[DescriptionResource] {
    path { descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) }
    def create = new DescriptionResource
  }

  this register new HandlerFactory[DescriptionModelResource] {
    path { descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) :: "/model" }
    def create = new DescriptionModelResource
  }

  this register new HandlerFactory[DescriptionDocumentationResource] {
    path { descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) :: "/documentation" }
    def create = new DescriptionDocumentationResource
  }

  this register new HandlerFactory[UserCollectionResource] {
    path { userCollectionPath }
    def create = new UserCollectionResource
  }

  start()
}

import org.dorest.server.rest._

class RootResource extends RESTInterface with HTMLSupport {

  get returns HTML { "Hello!" }

}

class DescriptionCollectionResource extends RESTInterface with DAO with XMLSupport {

  get returns XML {
    listDescriptions.toXML
  }

  post of XML returns XML {
    createDescription(Description(XMLRequestBody)).toXML
  }

}

class DescriptionResource extends RESTInterface with DAO with XMLSupport with Logging {

  var id: String = _

  get returns XML {
    findDescription(id).map(_.toXML)
  }

  delete {
    deleteDescription(id)
  }

}

class DescriptionModelResource extends RESTInterface with DAO with StreamSupport with XMLSupport {

  var id: String = _

  get returns ByteStream(MediaType.APPLICATION_XML) {
    findModel(id)
  }

  put of InputStream(MediaType.APPLICATION_XML) returns XML {
    updateModel(id, encodedInputStream)
    <updated/>
  }

  delete {
    deleteModel(id)
  }

}

class DescriptionDocumentationResource extends RESTInterface with DAO with StreamSupport with XMLSupport {

  var id: String = _

  get returns ByteStream(MediaType.APPLICATION_PDF) {
    findDocumentation(id)
  }

  put of InputStream(MediaType.APPLICATION_PDF) returns XML {
    updateDocumentation(id, inputStream)
    <updated/>
  }

  delete {
    deleteDocumentation(id)
  }

}

class UserCollectionResource extends RESTInterface with RegisteredUserAuthorization with DAO with XMLSupport {

  get returns XML { <hello>{ username }</hello> }

}

class UserResource extends RESTInterface with RegisteredUserAuthorization with TEXTSupport with XMLSupport {

  get returns XML { <hello>{ username }</hello> }

}

/**
 * Starts the SADServer as a configured stand-alone application.
 */
object SADServerApp extends scala.App with Logging {

  val configuration = new scala.sys.SystemProperties()
  configuration += ("org.tud.cs.st.opal.sadserver.database" -> "jdbc:h2:sads")

  SADServer

}
