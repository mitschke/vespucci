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
package de.tud.cs.st.vespucci.sadserver
import org.dorest.server.jdk.JDKServer
import org.dorest.server.log.Logging
import org.dorest.server.rest.representation.stream.StreamSupport
import org.dorest.server.rest._
import org.dorest.server.servlet.JettyServer
import org.dorest.server.HandlerFactory
import org.dorest.server.MediaType
import GlobalProperties.descriptionCollectionPath
import GlobalProperties.documentationPath
import GlobalProperties.modelPath
import GlobalProperties.port
import GlobalProperties.rootPath
import GlobalProperties.userCollectionPath
import GlobalProperties.transactionalPath
import org.dorest.server.rest.representation.multipart.{ Data, FormField }
import org.dorest.server.rest.representation.multipart.MultipartSupport
/**
 * Software Architecture Description Server
 *
 * @author Mateusz Parzonka
 */
object SADServer
  extends JettyServer(port)
  with DatabaseAccess
  with ShutdownListener
  with Logging {

  logger.info("Starting Software Architecture Description Server...")

  startDatabase()

  this register new HandlerFactory[RootResource] {
    path { rootPath }
    def create = new RootResource
  }

  // Description Resource //
  this register new HandlerFactory[DescriptionCollectionResource] {
    path { rootPath + descriptionCollectionPath }
    def create = new DescriptionCollectionResource with RestrictWriteToRegisteredUsers
  }
  this register new HandlerFactory[DescriptionResource] {
    path { rootPath + descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) }
    def create = new DescriptionResource with RestrictWriteToRegisteredUsers
  }
  this register new HandlerFactory[DescriptionModelResource] {
    path { rootPath + descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) :: modelPath }
    def create = new DescriptionModelResource
  }
  this register new HandlerFactory[DescriptionDocumentationResource] {
    path { rootPath + descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) :: documentationPath }
    def create = new DescriptionDocumentationResource
  }

  // Transactional Descriptions //
  this register new HandlerFactory[TransactionalDescriptionCollectionResource] {
    path { rootPath + transactionalPath + descriptionCollectionPath }
    def create = new TransactionalDescriptionCollectionResource with RestrictWriteToRegisteredUsers
  }
  this register new HandlerFactory[TempDescriptionResource] {
    path { rootPath + transactionalPath + descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) }
    def create = new TempDescriptionResource with RestrictWriteToRegisteredUsers
  }
  this register new HandlerFactory[TempModelResource] {
    path { rootPath + transactionalPath + descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) :: modelPath }
    def create = new TempModelResource with RestrictWriteToRegisteredUsers
  }
  this register new HandlerFactory[TempDocumentationResource] {
    path { rootPath + transactionalPath + descriptionCollectionPath :: "/" :: StringValue((desc, id) => desc.id = id) :: documentationPath }
    def create = new TempDocumentationResource with RestrictWriteToRegisteredUsers
  }

  //
  this register new HandlerFactory[UserCollectionResource] {
    path { rootPath + userCollectionPath }
    def create = new UserCollectionResource with RestrictToAdmins
  }
}

class RootResource extends RESTInterface with HTMLSupport {

  get returns HTML { "Hello!" }

}

class DescriptionCollectionResource extends RESTInterface with DatabaseAccess with TempDescription with XMLSupport with MultipartSupport {

  get returns XML {
    listDescriptions.toXML
  }

}

class DescriptionResource extends RESTInterface with DatabaseAccess with XMLSupport with MultipartSupport {

  var id: String = _

  get returns XML {
    findDescription(id).map(_.toXML)
  }

  delete {
    deleteDescription(id)
  }

}

class DescriptionModelResource extends RESTInterface with DatabaseAccess with StreamSupport with XMLSupport {

  var id: String = _

  get returns ByteStream(MediaType.APPLICATION_XML) {
    findModel(id)
  }

}

class DescriptionDocumentationResource extends RESTInterface with DatabaseAccess with StreamSupport with XMLSupport {

  var id: String = _

  get returns ByteStream(MediaType.APPLICATION_PDF) {
    findDocumentation(id)
  }

}

//////////////////////////////////////////////// TransactionResource /////////////////////////////////////////////

class TransactionalDescriptionCollectionResource extends RESTInterface with DatabaseAccess with TempDescription with XMLSupport with MultipartSupport {

  post of XML returns XML {
    Transaction(XMLRequestBody) match {
      case transaction if transaction.resourceId.isDefined =>
        findDescription(transaction.resourceId.get).map(description =>
          transaction.url = Some(createTemp(description)))
        transaction.toXML
      case transaction =>
        transaction.url = Some(createTemp(Description()))
        transaction.toXML
    }
  }

}

class TempDescriptionResource extends RESTInterface with TempDescription with DatabaseAccess with XMLSupport {

  var id: String = _

  /** stores the SAD to the database */
  post of XML returns XML {
    println("Committing...")
    deleteTemp(id).map(storeSAD(_).toXML)
  }

  /** updates the text fields of the SAD*/
  put of XML returns XML {
    val newDesc = Description(XMLRequestBody)
    getTemp(id).map(tempDesc => {
      tempDesc.name = newDesc.name
      tempDesc.`type` = newDesc.`type`
      tempDesc.`abstract` = newDesc.`abstract`
      tempDesc.toXML
    })
  }

  delete {
    deleteTemp(id); true
  }

}

class TempModelResource extends RESTInterface with TempDescription with MultipartSupport with XMLSupport {

  var id: String = _

  put of Multipart returns XML {
    getTemp(id).map(d => {
      d.model = Some(new Model(dataPart(MediaType.APPLICATION_XML)))
      d.toXML
    })
  }

}

class TempDocumentationResource extends RESTInterface with TempDescription with MultipartSupport with XMLSupport {

  var id: String = _

  put of Multipart returns XML {
    getTemp(id).map(d => {
      d.documentation = Some(new Documentation(dataPart(MediaType.APPLICATION_PDF)))
      d.toXML
    })
  }

}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

class UserCollectionResource extends RESTInterface with DatabaseAccess with XMLSupport {

  get returns XML {
    listUsers.toXML
  }

  post of XML returns XML {
    createUser(User(XMLRequestBody)).toXML
  }

}

class UserResource extends RESTInterface with DatabaseAccess with XMLSupport {

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
object SADServerApp extends scala.App {

  SADServer

}
