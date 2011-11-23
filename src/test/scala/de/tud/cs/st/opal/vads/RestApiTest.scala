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

@RunWith(classOf[JUnitRunner])
class RestApiTest extends FlatSpec with ShouldMatchers with BeforeAndAfterAll {

  override def beforeAll(configMap: Map[String, Any]) {
    import de.tud.cs.st.opal.vads.VespucciArchitectureDescriptionServer
    VespucciArchitectureDescriptionServer
  }

  import dispatch._
  import Http._

  "The root resource" should "return plain text on GET" in {
    Http(url("http://localhost:9000") <:< Map("Accept" -> "text/plain") as_str) should equal { "Hello!" }
  }
  
//  it should "return HTML on GET"
//
//  it should "return JSON on GET"
  
}
