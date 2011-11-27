package de.tud.cs.st.opal.sadserver

import org.dorest.server.auth._

trait AdminAuthorization extends BasicAuthentication
  with SimpleAuthenticator
  with AuthenticatedUser {

  def authenticationRealm = "http://www.opal-project.de/vespucci_project"

  val authorizationUser = "admin"
  val authorizationPwd = "password"

}

import org.scalaquery.session.Database._
import org.scalaquery.ql.extended.H2Driver.Implicit._
trait RegisteredUserAuthorization extends BasicAuthentication with DatabaseAccess {

  def authenticationRealm = "http://www.opal-project.de/vespucci_project"

  var username: String = _
  var password: String = _

  def authenticate(uname: String, pwd: String): Boolean = {

    username = uname
    password = pwd

    db withSession {
      val query = for { user <- USERS if user.username === uname if user.password === pwd } yield user
      !(query.list).isEmpty
    }
  }

}