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

import java.io.{ Reader, InputStream }
import scala.xml.Elem

// Description(s)

/**
 * @author Mateusz Parzonka
 */
object Description {
  def apply(xml: Elem): Description = {
    def parse(s: String): String = (xml \\ s).text
    new Description(
      { val parsed = (xml \ "@id").text; if (parsed.isEmpty) java.util.UUID.randomUUID().toString else parsed },
      parse("name"),
      parse("type"),
      parse("abstract"),
      None,
      None,
      { val parsed = (xml \\ "wip").text; if (!parsed.isEmpty) parsed.toBoolean else false })
  }
}

case class Description(
  val id: String,
  val name: String,
  val `type`: String,
  val `abstract`: String,
  val model: Option[(Reader, Int)],
  val documentation: Option[(InputStream, Int)],
  val wip: Boolean) {

  // TODO Provide (global?) access to authority and port
  val url: String = "http://localhost:9000" + SADServer.descriptionCollectionPath + id

  private def modelToXML =
    <model size={ model.get._2.toString }>
      <url>{ url + "/model" }</url>
    </model>

  private def documentationToXML =
    <documentation size={ documentation.get._2.toString }>
      <url>{ url + "/documentation" }</url>
    </documentation>

  def toXML: Elem =
    <description id={ id }>
      <url>{ url }</url>
      <name>{ name }</name>
      <type>{ `type` }</type>
      <abstract>{ `abstract` }</abstract>
      { if (model.isDefined) modelToXML }
      { if (documentation.isDefined) documentationToXML }
    </description>
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