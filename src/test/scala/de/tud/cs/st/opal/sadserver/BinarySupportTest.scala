package de.tud.cs.st.opal.sadserver

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.ShouldMatchers

import org.dorest.client.SimpleClient
import org.dorest.server.jdk.Server
import org.dorest.server._
import rest._

import org.apache.commons.io.{ IOUtils, FileUtils }

/**
 * Starts the SADServer as a configured stand-alone application.
 */
object BinarySupportTestServer extends Server(9000) {

  this register new HandlerFactory[Root] {
    path { "/" }
    def create = new Root
  }

  start()

  class Root extends RESTInterface with XMLSupport with BinarySupport {

    get returns Binary(MediaType.APPLICATION_PDF) {
      val is = new java.io.FileInputStream("src/test/resources/test.pdf")
      val bs = new java.io.BufferedInputStream(is)
      Some(bs, 17320)
    }

    get returns Binary(MediaType.IMAGE_PNG) {
      val is = new java.io.FileInputStream("src/test/resources/test.png")
      val bs = new java.io.BufferedInputStream(is)
      Some(bs, 9930)
    }

    put of BinaryIn(MediaType.APPLICATION_PDF) returns XML {
      FileUtils.writeByteArrayToFile(new java.io.File("temp/uploaded.pdf"), bytes)
      <success/>
    }

//    put of BinaryIn(MediaType.IMAGE_PNG) returns XML {
//      FileUtils.writeByteArrayToFile(new java.io.File("temp/uploaded.png"), bytes)
//      <success/>
//    }
  }

}

@RunWith(classOf[JUnitRunner])
class BinarySupportTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {

  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting BinarySupportTest")
    BinarySupportTestServer
  }

  val getPdf = SimpleClient.get(Map("Accept" -> "application/pdf")) _
  val getPng = SimpleClient.get(Map("Accept" -> "image/png")) _
  val putPdf = SimpleClient.putFile(Map("Accept" -> "application/xml"), "application/pdf") _
  val putPng = SimpleClient.putFile(Map("Accept" -> "application/xml"), "image/png") _

  "BinarySupport" should "allow a client to download a pdf as stream" in {
    val response = getPdf("http://localhost:9000")
    response.statusCode should equal(200)
    FileUtils.writeByteArrayToFile(new java.io.File("temp/downloaded.pdf"), response.bytes)
  }

  it should "allow a client to upload a pdf as file" in {
    val response = putPdf("http://localhost:9000", new java.io.File("src/test/resources/test.pdf"))
    response.statusCode should equal(200)
  }
  
  it should "allow a client to upload a png as file" in {
    val response = putPng("http://localhost:9000", new java.io.File("src/test/resources/test.png"))
    response.statusCode should equal(200)
  }

  it should "allow a client to download a png as file" in {
    val response = getPng("http://localhost:9000")
    response.statusCode should equal(200)
    FileUtils.writeByteArrayToFile(new java.io.File("temp/downloaded.png"), response.bytes)
  }

}

