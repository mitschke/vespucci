package de.tud.cs.st.opal.sadserver

import de.tud.cs.st.opal.jdbc.H2DatabaseConnection;
import de.tud.cs.st.opal.jdbc.JdbcSupport;
import com.weiglewilczek.slf4s.Logger
import java.io.{ InputStream, Reader }

object DAO extends DAO

trait DAO extends JdbcSupport with H2DatabaseConnection {

  private val logger = Logger("de.tud.cs.st.opal.sadserver.DAO")

  def startDatabase() {
    logger.info("Starting database")
    withTransaction {
      conn =>
        conn.executeUpdate("""
            CREATE TABLE users (
            id UUID PRIMARY KEY, 
            name VARCHAR(100), 
            password VARCHAR(50))
            """)
        conn.executeUpdate("""
            CREATE TABLE sads (
            id UUID PRIMARY KEY,
            name VARCHAR(100), 
            type VARCHAR(100), 
            abstract VARCHAR(150), 
            model BLOB, 
            documentation BLOB, 
            wip BOOLEAN)
            """)
    }
  }

  // Description-CRUD

  def createDescription(description: Description) =
    withPreparedStatement("INSERT INTO sads VALUES(?, ?, ?, ?, NULL, NULL, ?)") {
      ps =>
        ps.executeUpdateWith(description.id, description.name, description.`type`, description.`abstract`, description.wip)
        logger.debug("Created [%s]" format description)
        description
    }

  def findDescription(id: String): Option[Description] = withPreparedStatement("SELECT name, type, abstract, model, length(model), documentation, length(documentation), wip FROM SADs WHERE id = ?") {
    ps =>
      val rs = ps.executeQueryWith(id)
      val retrieved = if (rs.next) {
        Some(new Description(
          id,
          rs.getString("name"),
          rs.getString("type"),
          rs.getString("abstract"),
          if (rs.getString("model") != null) Some(rs.getCharacterStream("model") -> rs.getInt(5)) else None,
          if (rs.getString("documentation") != null) Some(rs.getBinaryStream("documentation") -> rs.getInt(7)) else None,
          rs.getBoolean("wip")));
      } else {
        None
      }
      logger.debug("Retrieved description [%s] using id [%s]" format (retrieved, id))
      retrieved
  }

  def updateDescription(id: String, description: Description): Description =
    withPreparedStatement("UPDATE sads SET name = ?, type = ?, abstract = ?, wip = ? WHERE id = ?") {
      ps =>
        ps.executeUpdateWith(description.name, description.`type`, description.`abstract`, description.wip, id)
        logger.debug("Updated description [%s] using id [%s]" format (description, id))
        description
    }

  def deleteDescription(id: String) = withPreparedStatement("DELETE FROM sads WHERE id = ?") {
    ps =>
      val result = ps.executeUpdateWith(id)
      logger.debug("Deleted description using id [%s]" format id)
      result
  }

  def listDescriptions = withQuery("SELECT id, name, type, abstract, model, length(model), documentation, length(documentation), wip FROM SADs") {
    rs =>
      var list: List[Description] = List[Description]()
      while (rs.next) {
        list = {
          new Description(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("type"),
            rs.getString("abstract"),
            if (rs.getString("model") != null) Some(rs.getCharacterStream("model") -> rs.getInt(6)) else None,
            if (rs.getString("documentation") != null) Some(rs.getBinaryStream("documentation") -> rs.getInt(8)) else None,
            rs.getBoolean("wip")) +: list
        }
      }
      println(list)
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

  def updateModel(id: String, blob: InputStream) = withPreparedStatement("UPDATE sads SET model = ? WHERE id = ?") {
    ps =>
      val result = ps.executeUpdateWith(blob, id)
      logger.debug("Updated model to [%s] using id [%s] => success=%s" format (blob, id, result))
      result
  }

  def deleteModel(id: String) = withPreparedStatement("UPDATE sads SET model = NULL WHERE id = ?") {
    ps =>
      val result = ps.executeUpdateWith(id)
      logger.debug("Deleted model [%s]" format id)
      result
  }

  // Documentation-RUD

  def findDocumentation(id: String) = withPreparedStatement("SELECT documentation, length(documentation) FROM sads WHERE id = ?") {
    _.executeQueryWith(id).nextTuple(blob, integer)
  }

  def updateDocumentation(id: String, blob: InputStream) = withPreparedStatement("UPDATE sads SET documentation = ? WHERE id = ?") {
    _.executeUpdateWith(blob, id)
  }

  def deleteDocumentation(id: String) = withPreparedStatement("UPDATE sads SET documentation = NULL WHERE id = ?") {
    _.executeUpdateWith(id)
  }

  // User-CRUD and authentification

//  def createUser(username: String, password: String) =
//    withPreparedStatement("INSERT INTO users VALUES(?, ?)") {
//      ps =>
//        ps.executeUpdateWith(description.id, description.name, description.`type`, description.`abstract`, description.wip)
//        logger.debug("Created [%s]" format username)
//        description
//    }
  
  def createUser(user: User) = withPreparedStatement("INSERT INTO users VALUES(?, ?, ?)") {
    ps =>
       ps.executeUpdateWith(user.id, user.name, user.password)
       logger.debug("Created [%s]" format user)
       user
  }
    

  def doesUserExist(username: String) = withPreparedStatement("SELECT * FROM users WHERE username = ?") {
    !_.executeQueryWith(username).isEmpty
  }

  def findPassword(username: String) = withPreparedStatement("SELECT password FROM users WHERE username = ?") {
    _.executeQueryWith(username).nextValue(string)
  }

  def adminPassword(username: String) = withQuery("SELECT password FROM users WHERE username = 'admin'") { _ nextValue (string) }

}