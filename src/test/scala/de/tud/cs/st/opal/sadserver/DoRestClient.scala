package de.tud.cs.st.opal.sadserver

import com.weiglewilczek.slf4s.Logging
import org.apache.http._
import org.apache.http.entity._
import auth._
import client.params._
import auth.params._
import impl.client._
import impl.auth._
import client._
import methods._

object DoRestClient {

  def get(headers: Map[String, String], auth: Auth = NoAuth)(url: String): Response = {
    execute(new HttpGet(url), headers, auth)
  }

  def post(headers: Map[String, String], auth: Auth = NoAuth)(url: String, content: String): Response = {
    val post: HttpPost = new HttpPost(url)
    post.setEntity(new StringEntity(content))
    execute(post, headers, auth)
  }

  private[this] def execute[T <: HttpRequestBase, S <: Auth](request: T, headers: Map[String, String], auth: S): Response = {
    auth.applyHeader(request)
    for ((key, value) <- headers) request.addHeader(key, value)
    val httpClient = new DefaultHttpClient()
    new Response(httpClient.execute(request))
  }

}

class Response(private val response: HttpResponse) {

  val statusCode: Int = response.getStatusLine().getStatusCode()

  val body: String = {
    val entity = response.getEntity()
    val content = entity.getContent()
    val body: String = util.EntityUtils.toString(entity)
    util.EntityUtils.consume(entity)
    body
  }

}

abstract class Auth {

  def applyHeader(httpRequest: HttpRequest): HttpRequest

}

object NoAuth extends Auth {

  def applyHeader(httpRequest: HttpRequest) = httpRequest

}

class BasicAuth(private val username: String, private val password: String) extends Auth {

  def applyHeader(httpRequest: HttpRequest) = {
    val header = new BasicScheme().authenticate(new UsernamePasswordCredentials(username, password), httpRequest)
    httpRequest.addHeader(header)
    httpRequest
  }

}

class DigestAuth(username: String, password: String) extends Auth {

  def applyHeader(httpRequest: HttpRequest) = {
    val header = new DigestScheme().authenticate(new UsernamePasswordCredentials(username, password), httpRequest)
    httpRequest.addHeader(header)
    httpRequest
  }

}



