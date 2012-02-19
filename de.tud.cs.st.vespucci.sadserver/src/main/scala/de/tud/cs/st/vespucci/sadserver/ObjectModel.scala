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

import java.io.{ Reader, InputStream }
import scala.xml.Elem
import GlobalProperties._
import scala.xml.NodeSeq
import org.dorest.server.rest.representation.multipart.MultipartIterator
import org.dorest.server.rest.representation.multipart.FormField
import org.dorest.server.rest.representation.multipart.Data
import org.dorest.server.MediaType
import java.util.UUID.randomUUID

// Description(s)

/**
 * @author Mateusz Parzonka
 */
object Description {
  def apply(): Description = Description(randomUUID.toString, "untitled", "", "", None, None, false, 0)
  def apply(xml: Elem): Description = {
    def parse(s: String): String = (xml \\ s).text
    new Description(
      { val parsed = (xml \ "@id").text; if (parsed.isEmpty) randomUUID.toString else parsed },
      parse("name"),
      parse("type"),
      parse("abstract"),
      { val parsed = (xml \\ "model"); if (parsed.isEmpty) None else Some(Model(null, (parsed \ "@name").text, (parsed \ "@size").text.toInt)) },
      { val parsed = (xml \\ "documentation"); if (parsed.isEmpty) None else Some(Documentation(null, (parsed \ "@name").text, (parsed \ "@size").text.toInt)) },
      { val parsed = (xml \\ "wip").text; if (!parsed.isEmpty) parsed.toBoolean else false },
      { val parsed = (xml \ "@modified").text; if (parsed.isEmpty) 0 else parsed.toLong })
  }
  def apply(xml: String): Description = Description(scala.xml.XML.loadString(xml))
}

case class Description(
  var id: String,
  var name: String,
  var `type`: String,
  var `abstract`: String,
  var model: Option[Model],
  var documentation: Option[Documentation],
  var wip: Boolean,
  var modified: Long) {

  def url: String = "http://" + authority + ":" + port + rootPath + descriptionCollectionPath + "/" + id

  def toXML: Elem =
    <description id={ id } modified={ modified.toString }>
      <url>{ url }</url>
      <name>{ name }</name>
      <type>{ `type` }</type>
      <abstract>{ `abstract` }</abstract>
      { if (model.isDefined) model.get.toXml }
      { if (documentation.isDefined) documentation.get.toXml }
    </description>

}

case class Model(var data: InputStream, var name: String, var size: Int) {
  def this(data: Data) = this(data.openStream, data.fileName, data.contentLength)
  def this() = this(null, "", 0)
  def toXml: Elem =
    <model name={ name } size={ size.toString }>
      <url>not set</url>
    </model>
}

case class Documentation(var data: InputStream, var name: String, var size: Int) {
  def this(data: Data) = this(data.openStream, data.fileName, data.contentLength)
  def this() = this(null, "", 0)
  def toXml: Elem =
    <documentation name={ name } size={ size.toString }>
      <url>not set</url>
    </documentation>
}

case class DescriptionCollection(val descriptionList: List[Description]) {

  def toXML: Elem =
    <descriptions>
      { for (d <- descriptionList) yield d.toXML }
    </descriptions>
}

// User(s)

object User {
  def apply(xml: Elem) = {
    def parse(s: String): String = (xml \\ s).text
    new User(
      { val parsed = (xml \ "@id").text; if (parsed.isEmpty) java.util.UUID.randomUUID().toString else parsed },
      parse("name"),
      parse("password"))
  }
}

case class User(val id: String, val name: String, val password: String) {

  def toXML: scala.xml.Elem =
    <user id={ id }>
      <name>{ name }</name>
    </user>

}

case class UserCollection(val userList: List[User]) {

  def toXML: Elem =
    <users>
      { for (user <- userList) yield user.toXML }
    </users>
}

case class Transaction(xml: Elem) {
  def this(xml: String) = this(scala.xml.XML.loadString(xml))
  def this() = this("<transaction/>")
  def parse(s: String): Option[String] = { val parsed = (xml \\ s); if (parsed.isEmpty) None else Some(parsed.text.toString) }
  var transactionResource = parse("transactionResource")
  var resourceId = parse("resourceId")
  var transactionId = parse("transactionId")
  def url =  transactionId.map("http://" + authority + ":" + port + rootPath + transactionalPath + descriptionCollectionPath + "/" + _)

  def toXML: scala.xml.Elem = {
    <transaction>
      { if (transactionResource.isDefined) <transactionResource>{ transactionResource.get }</transactionResource> }
      { if (resourceId.isDefined) <resourceId>{ resourceId.get }</resourceId> }
      { if (transactionId.isDefined) <transactionId>{ transactionId.get }</transactionId> }
      { if (transactionId.isDefined) <transactionUrl>{ url.get }</transactionUrl> }
    </transaction>
  }
}

