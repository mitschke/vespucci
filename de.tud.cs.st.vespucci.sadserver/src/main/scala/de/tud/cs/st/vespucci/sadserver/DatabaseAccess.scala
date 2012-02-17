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

object DatabaseAccess extends DatabaseAccess

/**
 * @author Mateusz Parzonka
 */
trait DatabaseAccess extends JdbcSupport with H2DatabaseConnection {

  private val logger = Logger("de.tud.cs.st.vespucci.sadserver.DatabaseAccess")

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

  def createDescription(description: Description) =
    withPreparedStatement("INSERT INTO sads VALUES(?, ?, ?, ?, NULL, NULL, NULL, NULL, ?, ?)") {
      ps =>
        ps.executeUpdateWith(description.id, description.name, description.`type`, description.`abstract`, description.wip, currentTimestamp)
        logger.debug("Created [%s]" format description)
        description
    }

  def findDescription(id: String): Option[Description] =
    withPreparedStatement("SELECT name, type, abstract, model, length(model), documentation, length(documentation), wip, modified FROM SADs WHERE id = ?") {
      ps =>
        val rs = ps.executeQueryWith(id)
        val retrieved = if (rs.next) {
          Some(new Description(
            id,
            rs.getString("name"),
            rs.getString("type"),
            rs.getString("abstract"),
            if (rs.getString("model") != null) Some(Model(rs.getCharacterStream("model"), "", rs.getInt(5))) else None, // FIXME
            if (rs.getString("documentation") != null) Some(Documentation(rs.getBinaryStream("documentation"), "", rs.getInt(7))) else None, // FIXME
            rs.getBoolean("wip"),
            rs.getTimestamp("modified").getTime()));
        } else {
          None
        }
        logger.debug("Retrieved description [%s] using id [%s]" format (retrieved, id))
        retrieved
    }

  def updateDescription(description: Description): Description =
    withPreparedStatement("UPDATE sads SET name = ?, type = ?, abstract = ?, wip = ? WHERE id = ?") {
      ps =>
        ps.executeUpdateWith(description.name, description.`type`, description.`abstract`, description.wip, description.id)
        logger.debug("Updated description [%s] using id [%s]" format (description, description.id))
        description
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
        list = {
          new Description(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("type"),
            rs.getString("abstract"),
            if (rs.getString("model") != null) Some(Model(rs.getCharacterStream("model"), "", rs.getInt(7))) else None, // FIXME
            if (rs.getString("documentation") != null) Some(Documentation(rs.getBinaryStream("documentation"), "", rs.getInt(10))) else None, // FIXME
            rs.getBoolean("wip"),
            rs.getTimestamp("modified").getTime()
            ) +: list
        }
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

  def updateModel(id: String, blob: InputStream) = withPreparedStatement("UPDATE sads SET model = ? WHERE id IS ?") {
    ps =>
      val result = ps.executeUpdateWith(blob, id) == 1
      logger.debug("Updated model to [%s] using id [%s] => success=%s" format (blob, id, result))
      result
  }

  def deleteModel(id: String) = withPreparedStatement("UPDATE sads SET model = NULL WHERE id = ?") {
    ps =>
      val result = ps.executeUpdateWith(id) == 1
      logger.debug("Deleted model [%s]" format id)
      result
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

  def updateDocumentation(id: String, blob: InputStream) = withPreparedStatement("UPDATE sads SET documentation = ? WHERE id IS ?") {
    ps =>
      val result = ps.executeUpdateWith(blob, id) == 1
      logger.debug("Updated documentation to [%s] using id [%s] => success=%s" format (blob, id, result))
      result
  }

  def deleteDocumentation(id: String) = withPreparedStatement("UPDATE sads SET documentation = NULL WHERE id = ?") {
    ps =>
      val result = ps.executeUpdateWith(id) == 1
      logger.debug("Deleted documentation [%s]" format id)
      result
  }

  // SAD - Update

  def updateSAD(description: Description) =
    withTransaction {
      conn =>
        
//        conn.prepareStatement("SELECT timestamp FROM sads WHERE id = ?")
//        .executeQueryWith(description.id).nextValue(long) match {
//          Some(modified: Long) if modified < 
//        }
          
        conn.prepareStatement("UPDATE sads SET name = ?, type = ?, abstract = ?, wip = ? WHERE id = ?")
          .executeQueryWith(description.name, description.`type`, description.`abstract`, description.wip, description.id)

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