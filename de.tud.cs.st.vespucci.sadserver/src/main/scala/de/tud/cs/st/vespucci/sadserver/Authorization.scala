/*
   Copyright 2011 Michael Eichberg et al
 
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
     http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package de.tud.cs.st.vespucci.sadserver

import org.dorest.server.auth._
import org.dorest.server._

trait RestrictToAdmins extends DigestAuthentication with DatabaseAccess {

  def authenticationRealm = GlobalProperties.authenticationRealm

  def password(username: String) = {
    val adminName = GlobalProperties.adminName
    val adminPassword = GlobalProperties.adminPassword
    username match {
      case username: String if username == adminName => Some(adminPassword)
      case _ => None
    }
  }
}

/**
 * GET operations for all, write operations for registered users found in the database.
 *
 * @author Mateusz Parzonka
 */
trait RestrictWriteToRegisteredUsers
  extends SuperHandler
  with DigestAuthentication
  with DatabaseAccess {

  val authenticationRealm = GlobalProperties.authenticationRealm

  def password(username: String) = {
    findPassword(username)
  }

  abstract override def processRequest(requestBody: => java.io.InputStream) = {
    method match {
      case GET => super[SuperHandler].processRequest(requestBody)
      case _ => super[DigestAuthentication].processRequest(requestBody)
    }
  }
}

trait SuperHandler extends Handler {
  abstract override def processRequest(requestBody: => java.io.InputStream): Response = super.processRequest(requestBody)
}