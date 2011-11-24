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

@RunWith(classOf[JUnitRunner])
class VADServerTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {

  override def beforeAll(configMap: Map[String, Any]) {

    import de.tud.cs.st.opal.vads.VADServer
    new VADServer
  }

  "The root resource" should "return a welcome in plain text on GET" in {
    Http(url("http://localhost:9000") <:< Map("Accept" -> "text/plain") as_str) should equal { "Hello!" }
  }

  "The descriptions resource" should "create a new description on POST via XML" in {
    Http(url("http://localhost:9000/descriptions") <:< Map("Accept" -> "application/xml") << "<xml/>" </> { nodes => (nodes \\ "id").text}) should equal { "42" }
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
