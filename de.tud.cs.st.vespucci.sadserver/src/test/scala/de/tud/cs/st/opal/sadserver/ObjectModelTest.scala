package de.tud.cs.st.opal.sadserver

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class ObjectModelTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {
  
  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting tests")
  }
  
   "Description" should "be constructed correctly from XML" in {
   val desc = Description(scala.xml.XML.loadFile("src/test/resources/existingDescription.xml"))
   desc.id should equal { "f81d4fae-7dec-11d0-a765-00a0c91e6bf6" }
   desc.name should equal { "myName" }
   desc.`type` should equal { "myType" }
   println(desc.toXML)
   
  }
  
}