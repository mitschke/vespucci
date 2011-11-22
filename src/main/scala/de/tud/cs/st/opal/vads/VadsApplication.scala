package de.tud.cs.st.opal.vads
import org.dorest.server.jdk.Server
import org.dorest.server.log.ConsoleLogging

/**
 * Vespucci Architecture Description Server
 */
object VespucciArchitectureDescriptionServer
  extends Server(9000)
  with scala.App
  with ConsoleLogging // TODO needs to exchanged
  {
  start()
  }

