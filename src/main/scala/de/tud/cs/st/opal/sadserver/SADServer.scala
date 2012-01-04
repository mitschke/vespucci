/*
   Copyright 2011 Michael Eichberg et al
 
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
     http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package de.tud.cs.st.opal.sadserver
import org.dorest.server.jdk.Server
import org.dorest.server.HandlerFactory
import com.weiglewilczek.slf4s.Logging
import org.dorest.server.rest._
import org.dorest.server.MediaType

/**
 * Software Architecture Description Server
 * 
 * @author Mateusz Parzonka
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
    def create = new DescriptionCollectionResource with RestrictWriteToRegisteredUsers
  }

  this register new HandlerFactory[DescriptionResource] {
    path { descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) }
    def create = new DescriptionResource with RestrictWriteToRegisteredUsers
  }

  this register new HandlerFactory[DescriptionModelResource] {
    path { descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) :: "/model" }
    def create = new DescriptionModelResource with RestrictWriteToRegisteredUsers
  }

  this register new HandlerFactory[DescriptionDocumentationResource] {
    path { descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) :: "/documentation" }
    def create = new DescriptionDocumentationResource with RestrictWriteToRegisteredUsers
  }

  this register new HandlerFactory[UserCollectionResource] {
    path { userCollectionPath }
    def create = new UserCollectionResource with RestrictToAdmins
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

class DescriptionResource extends RESTInterface with DAO with XMLSupport {

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
    if (updateModel(id, encodedInputStream)) <updated/> else None
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
    if (updateDocumentation(id, inputStream))  <updated/> else None
  }

  delete {
    deleteDocumentation(id)
  }

}

class UserCollectionResource extends RESTInterface with DAO with XMLSupport {
  
  post of XML returns XML {
    createUser(User(XMLRequestBody)).toXML
  }

}

class UserResource extends RESTInterface with DAO with XMLSupport {
  
  var id: String = _

  get returns XML {
    findUser(id).map(_.toXML)
  }
  
  delete {
    deleteUser(id)
  }

}

/**
 * Starts the SADServer as a configured stand-alone application.
 */
object SADServerApp extends scala.App with Logging {

  val configuration = new scala.sys.SystemProperties()
  configuration += ("org.tud.cs.st.opal.sadserver.database" -> "jdbc:h2:sads")

  SADServer

}
