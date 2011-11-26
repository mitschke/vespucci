package de.tud.cs.st.opal.vads

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

@RunWith(classOf[JUnitRunner])
class VADServerTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {

  override def beforeAll(configMap: Map[String, Any]) {
    println("Starting tests")
    new SystemProperties += ("org.tud.cs.st.opal.vads.database" -> "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1")
    import de.tud.cs.st.opal.vads.VADServer
    VADServer
  }

  var id1: String = _
  var id2: String = _

  "The descriptions resource" should "create a new description on POST via XML" in {
    id1 = Http(url("http://localhost:9000/descriptions") <:< Map("Accept" -> "application/xml") << "<xml/>" </> { nodes => (nodes \\ "id").text })
  }

  it should "create another description on POST via XML" in {
    id2 = Http(url("http://localhost:9000/descriptions") <:< Map("Accept" -> "application/xml") << "<xml/>" </> { nodes => (nodes \\ "id").text })
  }

  it should "return a list of all descriptions which where created on GET" in {
    Http(url("http://localhost:9000/descriptions") <:< Map("Accept" -> "text/plain") as_str) should equal { "SomeName" }
  }

  it should "provide a reference to the created description on GET providing its id" in {
    Http(url("http://localhost:9000/descriptions/" + id1) <:< Map("Accept" -> "text/plain") as_str) should equal { "SomeName" }
  }

}

/**
 * Enables control over logging.
 */
object Http extends DispatchHttp {

  override def make_logger = new Logger {
    def info(msg: String, items: Any*) {
    }

    def warn(msg: String, items: Any*) {
    }
  }
}
