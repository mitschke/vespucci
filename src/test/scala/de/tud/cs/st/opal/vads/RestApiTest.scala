package de.tud.cs.st.opal.vads

import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.{ResponseHandler, HttpClient}
import org.apache.http.protocol.HttpContext
import org.apache.http.{HttpRequest, HttpHost}
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class RestApiTest extends FlatSpec with ShouldMatchers {

  import dispatch._
  import org.apache.http.protocol.HTTP.CONTENT_ENCODING
  
  "1 + 1" should "equal 2" in {
    1 + 1  should equal { 2 }
  }

}