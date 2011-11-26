package de.tud.cs.st.opal.vads

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import SAD._

@RunWith(classOf[JUnitRunner])
class DescriptionTest extends FlatSpec with ShouldMatchers {
  
  val sad = xml.XML.loadFile("src/test/resources/mapping_detailed_description.sad")
      
  "The parsed SAD" should "contain a correct diagramId" in {
    SAD(sad).diagramId should equal { "_x5HuMF5MEeCxut-tIzAezA" }
  }
  
  it should "countain the correct diagamName" in {
     SAD(sad).diagramName should equal { "mapping.sad" }
  }

}
