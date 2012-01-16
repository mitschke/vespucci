package de.tud.cs.st.opal.jdbc

import java.sql.Connection
import org.h2.jdbcx.JdbcConnectionPool
import de.tud.cs.st.opal.sadserver.GlobalProperties._

object H2DatabaseConnection {

  //  "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"

  def cp = JdbcConnectionPool.create(databaseUrl, adminName, adminPassword)

}

trait H2DatabaseConnection {

  def connection: java.sql.Connection = {

    H2DatabaseConnection.cp.getConnection

  }

}