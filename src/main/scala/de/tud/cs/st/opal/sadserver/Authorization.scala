package de.tud.cs.st.opal.sadserver

import org.dorest.server.auth._
import org.dorest.server._

trait RestrictToAdmins extends DigestAuthentication with DAO {

  def authenticationRealm = "http://www.opal-project.de/vespucci_project"
    
  def password(username: String) = {
    username match {
      case "admin" => Some("password")
      case _ => None
    }
  }
     

}

/**
 * GET operations for all, write operations for registered users found in the database.
 */
trait RestrictWriteToRegisteredUsers
  extends SuperHandler
  with DigestAuthentication
  with DAO {

  def authenticationRealm = "http://www.opal-project.de/vespucci_project"

  var _username: String = _

  def username = _username

  def password(username: String) = {
    _username = username
    findPassword(username)
  }

  abstract override def processRequest(requestBody: java.io.InputStream) = {
    method match {
      case GET => super[SuperHandler].processRequest(requestBody)
      case _ => super[DigestAuthentication].processRequest(requestBody)
    }
  }
}

trait SuperHandler extends Handler {
  abstract override def processRequest(requestBody: java.io.InputStream): Response = super.processRequest(requestBody)
}