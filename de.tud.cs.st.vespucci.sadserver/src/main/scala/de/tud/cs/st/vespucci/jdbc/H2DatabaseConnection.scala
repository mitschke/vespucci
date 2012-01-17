package de.tud.cs.st.vespucci.jdbc

import java.sql.Connection
import org.h2.jdbcx.JdbcConnectionPool
import de.tud.cs.st.vespucci.sadserver.GlobalProperties._

object H2DatabaseConnection {

  def cp = JdbcConnectionPool.create(databaseUrl, adminName, adminPassword)

}

trait H2DatabaseConnection {

  def connection: java.sql.Connection = {

    H2DatabaseConnection.cp.getConnection

  }

}