package de.tud.cs.st.opal.sadserver

import de.tud.cs.st.opal.jdbc.H2DatabaseConnection;
import de.tud.cs.st.opal.jdbc.JdbcSupport;

trait DAO extends JdbcSupport with H2DatabaseConnection {

  def startTheDatabase() {
    withinSession { conn =>
      {
        conn prepareStatement ("create table users (id UUID, name VARCHAR(100), password VARCHAR(50))") execute;
        conn prepareStatement ("create table SADs (id UUID, name VARCHAR(100), type VARCHAR(100), abstract VARCHAR(150), data CLOB, pdf BLOB, isDraft BOOLEAN") execute
      }
    }
  }
  
  ///// User
  
//  def doesUserExist(username: String) = withinPreparedStatement("SELECT * FROM users WHERE username = ?")  { ps =>
//    {
//      val rs = ps executeQueryWith(username)
//      !(rs isEmpty)
//    }
//  }


}