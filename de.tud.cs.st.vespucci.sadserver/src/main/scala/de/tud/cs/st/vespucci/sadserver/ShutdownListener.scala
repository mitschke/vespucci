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
import org.dorest.server.jdk.JDKServer
import GlobalProperties.{ adminPort, rootPath, shutdownDelay }
import org.dorest.server.HandlerFactory
import org.dorest.server.rest.HTMLSupport
import org.dorest.server.rest.RESTInterface
import scala.xml.XML
import org.dorest.server.rest.XMLSupport
import org.dorest.server.log.Logger

/**
 * Implements a simple DoRest-server which listens on a separate port for shutdown requests.
 *
 * @author Mateusz Parzonka
 */
trait ShutdownListener {
  
  private[this] val logger = Logger(classOf[ShutdownListener])

  /**
   * A mixin is stoppable with given shutdown delay (seconds)
   */
  def stop(shutdownDelay: Int)

  val shutdownListener = new Server(adminPort) with Logging {

    this register new HandlerFactory[ShutdownResource] {
      path { rootPath + "/shutdown" }
      def create = new ShutdownResource with RestrictToAdmins
    }

    class ShutdownResource extends RESTInterface
      with XMLSupport {

      post of XML returns XML {
        shutdown()
        <server>Shutting down in {shutdownDelay} seconds...</server>
      }
    }
  }

  shutdownListener.start()

  def shutdown() {
    val shutdownThread = new Thread("Shutdown-Daemon") {
      override def run() {
        shutdownListener.stop(5)
        ShutdownListener.this.stop(shutdownDelay);
      }
    }
    shutdownThread.setDaemon(true)
    shutdownThread.start()
  }
}