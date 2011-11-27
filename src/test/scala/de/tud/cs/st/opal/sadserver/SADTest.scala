package de.tud.cs.st.opal.sadserver

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class SADTest extends FlatSpec with ShouldMatchers {
  
  val sad = xml.XML.loadFile("src/test/resources/sad1.sad")
      
  "The parsed SAD" should "contain a correct diagramId" in {
    SADParser(sad).diagramId should equal { "_x5HuMF5MEeCxut-tIzAezA" }
  }
  
  it should "countain the correct diagramName" in {
     SADParser(sad).diagramName should equal { "mapping.sad" }
  }

}
