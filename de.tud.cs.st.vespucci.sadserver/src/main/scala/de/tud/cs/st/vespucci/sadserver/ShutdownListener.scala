package de.tud.cs.st.vespucci.sadserver
import com.weiglewilczek.slf4s.Logging
import org.dorest.server.jdk.Server
import GlobalProperties.{ adminPort, rootPath, shutdownDelay }
import org.dorest.server.HandlerFactory
import org.dorest.server.rest.HTMLSupport
import org.dorest.server.rest.RESTInterface
import scala.xml.XML
import org.dorest.server.rest.XMLSupport

/**
 * Implements a simple DoRest-server which listens on a separate port for shutdown requests.
 *
 * @author Mateusz Parzonka
 */
trait ShutdownListener {

  /**
   * A mixin is stoppable with given shutdown delay (seconds)
   */
  def stop(shutdownDelay: Integer)

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