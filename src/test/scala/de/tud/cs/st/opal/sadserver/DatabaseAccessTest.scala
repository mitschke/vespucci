package de.tud.cs.st.opal.sadserver

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterAll

import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class DatabaseAccessTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {
  
  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting tests")
  }
  
   "It" should "work" in {
    true should equal  { true }
  }
  
}