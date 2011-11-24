package de.tud.cs.st.opal.vads
import org.dorest.server.jdk.Server
import org.dorest.server.HandlerFactory
import org.dorest.server.log.ConsoleLogging

/**
 * Vespucci Architecture Description Server
 */
class VADServer
  extends Server(9000)
  with ConsoleLogging // TODO needs to exchanged
  {
  
  def root = "/"

  this register new HandlerFactory[Root] {
    path {root}
    def create = new Root
  }
  
   this register new HandlerFactory[Descriptions] {
    path {root :: "descriptions"}
    def create = new Descriptions
  }
   
    this register new HandlerFactory[Description] {
    path {root :: "descriptions" :: LongValue((desc, longid) => desc.id = longid)}
    def create = new Description
  }
  
  start()
}

import org.dorest.server.rest._

class Root extends RESTInterface with TEXTSupport {

  get returns TEXT { "Hello!" }

}

class Descriptions extends RESTInterface with TEXTSupport with XMLSupport{
  
    post of XML returns XML { <success><id>42</id></success> }

}

class Description extends RESTInterface with TEXTSupport with XMLSupport {
  
  var id: Long = _
  
  
}

object VADServerApp extends VADServer with scala.App


