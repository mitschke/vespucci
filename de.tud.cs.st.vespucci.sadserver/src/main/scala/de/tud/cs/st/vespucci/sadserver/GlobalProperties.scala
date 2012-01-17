package de.tud.cs.st.vespucci.sadserver

import sys.props

/**
 * Encapsulates global properties into a static point of access.
 * 
 * @author Mateusz Parzonka
 */
object GlobalProperties {

  //  private val base = this.getClass().getPackage().getName() + "."
  private val base = "de.tud.cs.st.vespucci.sadserver."

  def authority = prop("authority")
  def port = prop("port").toInt
  def authenticationRealm = prop("authenticationRealm")
  
  def rootPath = prop("rootPath")
  def descriptionCollectionPath = prop("descriptionCollectionPath")
  def userCollectionPath = prop("userCollectionPath")
  def modelPath = prop("modelPath")
  def documentationPath = prop("documentationPath")

  def databaseUrl = prop("databaseUrl")
  def adminName = prop("adminName")
  def adminPassword = prop("adminPassword")

  def loadPropertiesFile(filePath: String) {
    import java.io.File
    import java.io.FileInputStream
    val f = new File(filePath);
    if (!f.exists()) {
      System.err.println("No configuration file found.")
      return
    }
    println("Loading configuration: " + f.getAbsolutePath() + ".");
    var fin: FileInputStream = null;
    try {
      fin = new FileInputStream(f);
      System.getProperties().load(fin);
      println("Configuration file loaded.");
    } finally {
      if (!(fin eq null))
        fin.close();
    }
  }

  def prop(key: String): String = props.get(base + key).getOrElse("")

  case class GlobalPropertiesException(key: String) extends Exception("Property with name [%s] not found!" format base + key)

}