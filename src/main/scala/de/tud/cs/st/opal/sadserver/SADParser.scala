package de.tud.cs.st.opal.sadserver

import scala.xml.Elem

object SADParser {

  // namespaces
  private val xmi = "@{http://www.omg.org/XMI}" + _
  private val xsi = "@{http://www.w3.org/2001/XMLSchema-instance}" + _
  private val notation = "@{http://www.eclipse.org/gmf/runtime/1.0.2/notation}" + _

  def apply(x: Elem) = new SADParser(x)

  class SADParser(xml: Elem) {

    lazy val xmlData = xml.toString
    lazy val diagramId = (xml \ "Diagram" \ xmi("id")).toString
    lazy val diagramName = (xml \ "Diagram" \ "@name").toString

    override def toString = {
      "SAD[diagramId=\"" + diagramId + "\", diagramName=\"" + diagramName + "\"]"
    }

  }

}

