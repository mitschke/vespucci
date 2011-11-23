package de.tud.cs.st.opal.vads
import org.dorest.server.jdk.Server
import org.dorest.server.HandlerFactory
import org.dorest.server.log.ConsoleLogging

/**
 * Vespucci Architecture Description Server
 */
object VespucciArchitectureDescriptionServer
  extends Server(9000)
//  with scala.App
  with ConsoleLogging // TODO needs to exchanged
  {

  this register new HandlerFactory[Welcome] {
    path {"/"}
    def create = new Welcome
  }

  start()
}

import org.dorest.server.rest.{ RESTInterface, TEXTSupport }

class Welcome extends RESTInterface with TEXTSupport {

  get returns TEXT { "Hello!" }

}
