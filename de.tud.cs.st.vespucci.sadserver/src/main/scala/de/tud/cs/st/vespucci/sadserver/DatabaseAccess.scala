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

import de.tud.cs.st.vespucci.jdbc.H2DatabaseConnection
import de.tud.cs.st.vespucci.jdbc.JdbcSupport
import java.io.{ InputStream, Reader }
import java.sql.ResultSet
import org.dorest.server.log.Logger
import java.sql.Timestamp
import java.sql.Connection
import org.dorest.server.RequestException
import org.dorest.server.ErrorResponse

object DatabaseAccess extends DatabaseAccess

/**
 * @author Mateusz Parzonka
 */
trait DatabaseAccess extends JdbcSupport with H2DatabaseConnection {

  private val logger = Logger(classOf[DatabaseAccess])

  def startDatabase() {
    logger.info("Opening database...")
    withTransaction {
      conn =>
        conn.executeUpdate("""
            CREATE TABLE IF NOT EXISTS users (
            id UUID PRIMARY KEY, 
            name VARCHAR(100), 
            password VARCHAR(50))
            """)
        conn.executeUpdate("""
            CREATE TABLE IF NOT EXISTS sads (
            id UUID PRIMARY KEY,
            name VARCHAR(100) NOT NULL, 
            type VARCHAR(100), 
            abstract VARCHAR(150),
            modelName VARCHAR (100),
            model BLOB, 
            documentationName VARCHAR (100),
            documentation BLOB, 
            wip BOOLEAN,
            modified TIMESTAMP)
            """)

        // TODO creating a temporary user, remove this in production code!
        val result: ResultSet = conn.executeQuery("SELECT * FROM users WHERE name = 'somebody'")
        if (result.isEmpty())
          conn.executeUpdate("INSERT INTO users VALUES('someid', 'somebody', 'password')")
        result.close()

    }
  }

  // Description-CRUD

  def findDescription(id: String): Option[Description] =
    withPreparedStatement("SELECT name, type, abstract, modelName, model, documentationName, documentation, wip, modified FROM SADs WHERE id = ?") {
      ps =>
        val rs = ps.executeQueryWith(id)
        val retrieved = if (rs.next) Some(description(id, rs)) else None
        logger.info("Retrieved description [%s] using id [%s]" format (retrieved, id))
        retrieved
    }

  private[this] def description(id: String, rs: ResultSet) = {
    new Description(
      id,
      rs.getString("name"),
      rs.getString("type"),
      rs.getString("abstract"),
      if (rs.getString("modelName") != null) Some(Model(rs.getBinaryStream("model"), rs.getString("modelName"))) else None,
      if (rs.getString("documentationName") != null) Some(Documentation(rs.getBinaryStream("documentation"), rs.getString("documentationName"))) else None,
      rs.getBoolean("wip"),
      rs.getTimestamp("modified").getTime())
  }

  def deleteDescription(id: String) = withPreparedStatement("DELETE FROM sads WHERE id = ?") {
    ps =>
      val result = ps.executeUpdateWith(id) == 1
      logger.debug("Deleted description using id [%s]" format id)
      result
  }

  def listDescriptions = withQuery("SELECT id, name, type, abstract, modelName, model, length(model), documentationName, documentation, length(documentation), wip, modified FROM SADs") {
    rs =>
      var list: List[Description] = List[Description]()
      while (rs.next) {
        val id = rs.getString("id")
        list = description(id, rs) +: list
      }
      new DescriptionCollection(list)
  }

  // Model-RUD

  def findModel(id: String) = withPreparedStatement("SELECT model, length(model) FROM SADs WHERE id = ?") {
    ps =>
      val rs = ps.executeQueryWith(id)
      val model = ps.executeQueryWith(id).nextTuple(blob, integer) match {
        case some @ Some((_, length)) if length > 0 => some
        case _ => None
      }
      logger.debug("Retrieved model [%s] using id [%s]" format (model, id))
      model
  }

  // Documentation-RUD

  def findDocumentation(id: String) = withPreparedStatement("SELECT documentation, length(documentation) FROM sads WHERE id = ?") {
    ps =>
      val rs = ps.executeQueryWith(id)
      val documentation = ps.executeQueryWith(id).nextTuple(blob, integer) match {
        case some @ Some((_, length)) if length > 0 => some
        case _ => None
      }
      logger.debug("Retrieved documentation [%s] using id [%s]" format (documentation, id))
      documentation
  }

  // SAD - Update

  def storeSAD(description: Description): Description =
    withTransaction {

      conn =>

        val modelName = if (description.model.isDefined) description.model.get.name else null
        val model = if (description.model.isDefined) description.model.get.data else null
        val documentationName = if (description.documentation.isDefined) description.documentation.get.name else null
        val documentation = if (description.documentation.isDefined) description.documentation.get.data else null

        def execute(statement: String) {
          conn.prepareStatement(statement).executeUpdateWith(description.name, description.`type`, description.`abstract`,
            modelName, model, documentationName, documentation, description.wip, currentTimestamp)
        }

        val remoteModified = new Timestamp(description.modified)
        conn.prepareStatement("SELECT modified FROM sads WHERE id = ?")
          .executeQueryWith(description.id).nextValue(timestamp) match {
            case None =>
              execute("INSERT INTO sads VALUES('%s', ?, ?, ?, ?, ?, ?, ?, ?, ?)" format description.id)
              logger.info("Created new SAD [%s]" format description)
            case Some(modified: Timestamp) if (modified.after(remoteModified)) =>
              description.renewId()
              description.name = description.name + " [EDIT RACE VERSION]"
              val message = "Edit race. Someone has already stored a version of this SAD in the meantime. " +
              " Your update was stored as a duplicate, please review both changes."
              execute("INSERT INTO sads VALUES('%s', ?, ?, ?, ?, ?, ?, ?, ?, ?)" format description.id)
              conn.commit() // because we need to throw an exception
              logger.warn("Edit race, stored duplicate [%s]" format description)
              throw new RequestException(response = new ErrorResponse(409, message)) 
            case Some(modified: Timestamp) =>
              execute("UPDATE sads SET name = ?, type = ?, abstract = ?, modelName = ?, model = ?, documentationName = ?, documentation = ?, wip = ?, modified = ? WHERE id = '%s'" format description.id)
              logger.info("Updated SAD [%s]" format description)
          }
        description
    }

  // User-CRUD and authentification

  def createUser(user: User) = withPreparedStatement("INSERT INTO users VALUES(?, ?, ?)") {
    ps =>
      ps.executeUpdateWith(user.id, user.name, user.password)
      logger.debug("Created [%s]" format user)
      user
  }

  def findUser(id: String) = withPreparedStatement("SELECT * FROM users WHERE id = ?") {
    ps =>
      val user = ps.executeQueryWith(id).nextTuple(string, string, string) match {
        case Some((id, name, password)) => Some(new User(id, name, password))
        case None => None
      }
      logger.debug("Retrieved user [%s] using id [%s]" format (user, id))
      user
  }

  def deleteUser(id: String) = withPreparedStatement("DELETE FROM users WHERE id = ?") {
    ps =>
      val result = ps.executeUpdateWith(id) == 1
      logger.debug("Deleted user using id [%s]" format id)
      result
  }

  def listUsers() = withQuery("SELECT * FROM users") {
    rs =>
      var list: List[User] = List[User]()
      while (rs.next) {
        list = {
          new User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("password")) +: list
        }
      }
      new UserCollection(list)
  }

  def doesUserExist(username: String) = withPreparedStatement("SELECT * FROM users WHERE username = ?") {
    !_.executeQueryWith(username).isEmpty
  }

  def findPassword(username: String) = withPreparedStatement("SELECT password FROM users WHERE name = ?") {
    _.executeQueryWith(username).nextValue(string)
  }

  def adminPassword(username: String) = withQuery("SELECT password FROM users WHERE username = 'admin'") { _ nextValue (string) }

  def currentTimestamp: Timestamp = new Timestamp(System.currentTimeMillis())

}