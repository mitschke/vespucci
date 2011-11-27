package de.tud.cs.st.opal.sadserver

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.ShouldMatchers
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.{ ResponseHandler, HttpClient }
import org.apache.http.protocol.HttpContext
import org.apache.http.{ HttpRequest, HttpHost }
import dispatch.{ Http => DispatchHttp, _ }
import DispatchHttp._
import scala.sys.SystemProperties

import scala.xml.{ XML, Utility }

@RunWith(classOf[JUnitRunner])
class SADServerTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {

  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting tests")
    new SystemProperties += ("org.tud.cs.st.opal.sadserver.database" -> "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1")
    SADServer
  }

  var id1: String = _
  var id2: String = _

  val sad1 = XML.loadFile("src/test/resources/sad1.sad")
  val sad2 = XML.loadFile("src/test/resources/sad2.sad")

  "The descriptions resource" should "create a SAD on POST via XML" in {
    id1 = Http(url("http://localhost:9000/sads") <:< Map("Accept" -> "application/xml") << sad1.toString </> { xml => (xml \\ "id").text })
  }

  it should "return the created SAD on GET providing its id" in {
    val sad: xml.Elem = Http(url("http://localhost:9000/sads/" + id1) <:< Map("Accept" -> "application/xml") <> { xml => xml })
    SAD(sad).diagramName should equal { "mapping.sad" }
    scala.xml.XML.save("temp/sad1.xml", sad, "UTF-8", true, null)
  }
  
  it should "create another SAD on POST via XML" in {
    id2 = Http(url("http://localhost:9000/sads") <:< Map("Accept" -> "application/xml") << sad2.toString </> { xml => (xml \\ "id").text })
  }
  
  it should "return the created second SAD on GET providing its id" in {
    val sad: xml.Elem = Http(url("http://localhost:9000/sads/" + id1) <:< Map("Accept" -> "application/xml") <> { xml => xml })
    SAD(sad).diagramName should equal { "mapping.sad" }
    scala.xml.XML.save("temp/sad2.xml", sad, "UTF-8", true, null)
  }
  
  it should "return a list of created SADs on GET" in {
     val sad: xml.Elem = Http(url("http://localhost:9000/sads") <:< Map("Accept" -> "application/xml") <> { xml => xml })
  }
  
  

}

/**
 * Enables control over logging.
 */
object Http extends DispatchHttp {

  override def make_logger = new Logger {
    def info(msg: String, items: Any*) {
      // no-op
    }

    def warn(msg: String, items: Any*) {
      // no-op
    }
  }
}
