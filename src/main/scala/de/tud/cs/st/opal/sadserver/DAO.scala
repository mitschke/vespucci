package de.tud.cs.st.opal.sadserver

import de.tud.cs.st.opal.jdbc.H2DatabaseConnection;
import de.tud.cs.st.opal.jdbc.JdbcSupport;
import de.tud.cs.st.opal.sdbc._

trait DAO extends JdbcSupport with H2DatabaseConnection {

  def startTheDatabase() {
    withinSession { conn =>
      {
        println("initializing db")
      }
    }
  }

  def doesUserExist(username: String) = withinSession { conn =>
    {
      val rs = conn prepareStatement ("SELECT * FROM users WHERE username = ?") executeQueryWith (string(username))
      !(rs isEmpty)
    }
  }

  def getPassword(username: String): Option[String] = withinSession { conn =>
    {
      val rs = conn prepareStatement ("SELECT * FROM users WHERE username = ?") executeQueryWith (string(username))
      val t = rs getFromNextRowAndClose (string("password"))
      t map (x => x._1)
    }
  }

  def updateUser(id: String, username: String, password: String) = withinSession { conn =>
    {
      conn prepareStatement ("table users update") executeUpdateWith (string(id), string(username), string(password)) close
    }
  }

}