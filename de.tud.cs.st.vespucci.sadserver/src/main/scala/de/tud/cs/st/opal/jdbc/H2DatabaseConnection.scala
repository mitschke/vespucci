package de.tud.cs.st.opal.jdbc

import java.sql.Connection
import org.h2.jdbcx.JdbcConnectionPool

object H2DatabaseConnection {

  private val cp = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "admin", "admin")
  
}

trait H2DatabaseConnection {

  final def connection: java.sql.Connection = { 
   
    H2DatabaseConnection.cp.getConnection
    
  }

}