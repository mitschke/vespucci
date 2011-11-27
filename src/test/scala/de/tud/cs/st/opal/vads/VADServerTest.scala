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

import scala.xml.{ XML, Utility }

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

  val source = scala.io.Source.fromFile("src/test/resources/mapping_detailed_description.sad")
  val lines = source.mkString
  source.close()
  val sad1 = XML.loadString(lines)
  val sad1expectedId = "_x5HuMF5MEeCxut-tIzAezA"

  "The descriptions resource" should "create a new description on POST via XML" in {
    id1 = Http(url("http://localhost:9000/descriptions") <:< Map("Accept" -> "application/xml") << lines </> { xml => (xml \\ "id").text })
    id1 should equal { sad1expectedId }
  }

  it should "provide a reference to the created description on GET providing its id" in {
    import Utility.trim
    val e: xml.Node = Http(url("http://localhost:9000/descriptions/" + id1) <:< Map("Accept" -> "application/xml") <> { xml => xml })
    Utility.trim(e) should equal { Utility.trim(sad1) }
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
