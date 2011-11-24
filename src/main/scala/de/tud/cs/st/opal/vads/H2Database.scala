package de.tud.cs.st.opal.vads

trait H2Database {
  
  import org.scalaquery.session.Database
  def db: Database = Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver")
  
  def print = println("I'm h2!")

}