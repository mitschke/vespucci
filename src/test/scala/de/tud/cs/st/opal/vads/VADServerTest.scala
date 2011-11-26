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
    new SystemProperties += ("org.tud.cs.st.opal.vads.database" -> "jdbc:h2:testdb;DB_CLOSE_DELAY=-1")
    import de.tud.cs.st.opal.vads.VADServer
    VADServer
  }

  var id1: String = _
  var id2: String = _
  val sad = xml.XML.loadFile("src/test/resources/mapping_detailed_description.sad")

  "The descriptions resource" should "create a new description on POST via XML" in {
    id1 = Http(url("http://localhost:9000/descriptions") <:< Map("Accept" -> "application/xml") << sad.toString </> { xml => (xml \\ "id").text })
    id1 should equal { "_x5HuMF5MEeCxut-tIzAezA" }
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
