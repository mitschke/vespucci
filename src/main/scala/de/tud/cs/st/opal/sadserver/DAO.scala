package de.tud.cs.st.opal.sadserver

import de.tud.cs.st.opal.jdbc.H2DatabaseConnection;
import de.tud.cs.st.opal.jdbc.JdbcSupport;
import java.io.{InputStream, Reader}

trait DAO extends JdbcSupport with H2DatabaseConnection {

  def startTheDatabase() {
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
            model CLOB, 
            documentation BLOB, 
            wip BOOLEAN)
            """)
    }
  }

  def doesUserExist(username: String) = withPreparedStatement("SELECT * FROM users WHERE username = ?") {
    !_.executeQueryWith(username).isEmpty
  }

  def password(username: String) = withPreparedStatement("SELECT password FROM users WHERE username = ?") {
    _.executeQueryWith(username).nextValue(string)
  }

  /**
   * Example
   */
  def adminPassword(username: String) = withQuery("SELECT password FROM users WHERE username = 'admin'") { _ nextValue (string) }

  def sad(id: String) = withPreparedStatement("SELECT name, type, abstract, model, documentation, wip FROM SADs WHERE id = ?") {
//    _.executeQueryWith(id).nextTuple(string, string)
    _.executeQueryWith(id).nextTuple(string, string, string, clob, blob, boolean)
  }
  
  /**
   * Stores a new sad with a random UUID and given parameters.
   */
  def sad_=(name: String, `type`: String, `abstract`: String, model: Reader, documentation: InputStream, wip: Boolean) =
    withPreparedStatement("INSERT sads VALUES(?, ?, ?, ?. ?, ?, ?)") {
    _.executeUpdateWith(uuid, name, `type`, `abstract`, model, documentation, wip)
  }

  def documentation(id: String) = withPreparedStatement("SELECT pdf FROM SADs WHERE id = ?") {
    _.executeQueryWith(id).nextValue(blob)
  }

  def updateDocumentation(id: String, blob: java.io.InputStream) = withPreparedStatement("SELECT pdf FROM SADs WHERE id = ?") {
    _.executeUpdateWith(id, blob)
  }

}