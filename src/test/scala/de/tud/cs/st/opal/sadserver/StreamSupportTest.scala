package de.tud.cs.st.opal.sadserver

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.ShouldMatchers

import org.dorest.client.SimpleClient
import org.dorest.client.Entity
import org.dorest.server.jdk.Server
import org.dorest.server._
import rest._
import java.io._

import org.apache.commons.io.{ IOUtils, FileUtils }

/**
 * Starts the SADServer as a configured stand-alone application.
 */
object StreamSupportTestServer extends Server(9000) {

  this register new HandlerFactory[ByteStreamResource] {
    path { "/bytestream" }
    def create = new ByteStreamResource
  }

  this register new HandlerFactory[ByteStreamBytesResource] {
    path { "/bytestream/bytes" }
    def create = new ByteStreamBytesResource
  }

  this register new HandlerFactory[CharacterStreamResource] {
    path { "/characterstream/" :: StringValue((cs, enc) => cs.encoding = enc) }
    def create = new CharacterStreamResource
  }

  start()

  class ByteStreamResource extends RESTInterface with XMLSupport with StreamSupport {

    def byteStream(file: String) = new BufferedInputStream(new FileInputStream(file))

    get returns ByteStream(MediaType.APPLICATION_PDF) {
      Some(byteStream("src/test/resources/test.pdf"), 17320)
    }

    get returns ByteStream(MediaType.IMAGE_PNG) {
      Some(byteStream("src/test/resources/test.png"), 9930)
    }

    def writeByteStream(inputStream: InputStream, file: String) = {
      val outputStream = new BufferedOutputStream(new FileOutputStream(file), 4096)
      IOUtils.copy(inputStream, outputStream)
      outputStream.close()
    }

    put of InputStream(MediaType.APPLICATION_PDF) returns XML {
      writeByteStream(inputStream, "temp/uploaded (bytestream).pdf")
      <success/>
    }

    put of InputStream(MediaType.IMAGE_PNG) returns XML {
      writeByteStream(inputStream, "temp/uploaded (bytestream).png")
      <success/>
    }

  }

  class ByteStreamBytesResource extends RESTInterface with XMLSupport with StreamSupport {

    put of InputStream(MediaType.APPLICATION_PDF) returns XML {
      FileUtils.writeByteArrayToFile(new java.io.File("temp/uploaded (bytes).pdf"), bytes)
      <success/>
    }

    put of InputStream(MediaType.IMAGE_PNG) returns XML {
      FileUtils.writeByteArrayToFile(new java.io.File("temp/uploaded (bytes).png"), bytes)
      <success/>
    }
  }

  /**
   * We write files with different encodings as UTF-8
   */
  class CharacterStreamResource extends RESTInterface with XMLSupport with StreamSupport {

    var encoding: String = _

    put of InputStream(MediaType.APPLICATION_XML) returns XML {
      FileUtils.writeStringToFile(new File("temp/uploaded_%s->utf-8 (characterStream).xml" format encoding),
        IOUtils.toString(reader), "UTF-8")
      <success/>
    }

    put of InputStream(MediaType.TEXT_PLAIN) returns XML {
      FileUtils.writeStringToFile(new File("temp/uploaded_%s->utf-8 (characterStream).txt" format encoding),
        IOUtils.toString(reader), "UTF-8")
      <success/>
    }

    get returns CharacterStream(MediaType.APPLICATION_XML) {
      val fis = new FileInputStream("src/test/resources/test_utf-8.xml");
      val isr = new InputStreamReader(fis, "UTF-8");
      val bsr = new BufferedReader(isr);
      (bsr, 60)
    }

  }

}

@RunWith(classOf[JUnitRunner])
class StreamSupportTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {

  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting StreamSupportTest")
    StreamSupportTestServer
  }

  def get(contentType: String) = SimpleClient.get(Map("Accept" -> contentType)) _
  val put = SimpleClient.put(Map("Accept" -> "application/xml")) _

  "StreamSupport with bytestream" should "allow a client to GET a pdf" in {
    val response = get("application/pdf")("http://localhost:9000/bytestream")
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/pdf; charset=UTF-8" }
    org.apache.commons.io.FileUtils.writeByteArrayToFile(new java.io.File("temp/downloaded.pdf"), response.bytes)
  }

  it should "allow a client to GET a png" in {
    val response = get("image/png")("http://localhost:9000/bytestream")
    response.statusCode should equal { 200 }
    response.contentType should equal { "image/png; charset=UTF-8" }
    FileUtils.writeByteArrayToFile(new java.io.File("temp/downloaded.png"), response.bytes)
  }

  it should "allow a client to PUT a pdf" in {
    val response = put("http://localhost:9000/bytestream", Entity(new File("src/test/resources/test.pdf"), "application/pdf"))
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
  }

  it should "allow a client to PUT a png" in {
    val response = put("http://localhost:9000/bytestream", Entity(new File("src/test/resources/test.png"), "image/png"))
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
  }

  "StreamSupport with bytes" should "allow a client to PUT a pdf" in {
    val response = put("http://localhost:9000/bytestream/bytes", Entity(new File("src/test/resources/test.pdf"), "application/pdf"))
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
  }

  it should "allow a client to PUT a png" in {
    val response = put("http://localhost:9000/bytestream/bytes", Entity(new File("src/test/resources/test.png"), "image/png"))
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
  }

  "StreamSupport with characterStream" should "allow a client to PUT xml using UTF-8" in {
    val response = put("http://localhost:9000/characterstream/UTF-8", Entity(new File("src/test/resources/test_utf-8.xml"), "application/xml; charset=UTF-8"))
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
  }

  it should "allow a client to PUT xml using ISO-8859-1" in {
    val response = put("http://localhost:9000/characterstream/ISO-8859-1", Entity(new File("src/test/resources/test_ISO-8859-1.xml"), "application/xml; charset=ISO-8859-1"))
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
  }

  it should "allow a client to PUT text using UTF-8" in {
    val response = put("http://localhost:9000/characterstream/UTF-8", Entity(new File("src/test/resources/test_utf-8.txt"), "text/plain; charset=UTF-8"))
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
  }

  it should "allow a client to PUT text using ISO-8859-1" in {
    val response = put("http://localhost:9000/characterstream/ISO-8859-1", Entity(new File("src/test/resources/test_ISO-8859-1.txt"), "text/plain; charset=ISO-8859-1"))
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
  }

  it should "allow a client to GET xml using UTF-8" in {
    val response = get("application/xml")("http://localhost:9000/characterstream/UTF-8")
    response.statusCode should equal { 200 }
    response.contentType should equal { "application/xml; charset=UTF-8" }
    println(response.body)
  }

}

