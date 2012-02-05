package de.tud.cs.st.vespucci.sadserver

import sys.props

/**
 * Encapsulates global properties into a static point of access.
 *
 * @author Mateusz Parzonka
 */
object GlobalProperties {

  // all properties are loaded immediately
  loadPropertiesFile("sadserver.properties")
  
  //  private val base = this.getClass().getPackage().getName() + "."
  private val base = "de.tud.cs.st.vespucci.sadserver."

  def authority = prop("authority")
  def port = prop("port").toInt
  def authenticationRealm = prop("authenticationRealm")

  // paths must be all stored in the form "/somePath"
  def rootPath = prop("rootPath")
  def descriptionCollectionPath = prop("descriptionCollectionPath")
  def userCollectionPath = prop("userCollectionPath")
  def modelPath = prop("modelPath")
  def documentationPath = prop("documentationPath")

  def databaseUrl = prop("databaseUrl")
  def adminName = prop("adminName")
  def adminPassword = prop("adminPassword")
  def adminPort = prop("adminPort").toInt
  def shutdownDelay = prop("shutdownDelay").toInt

  def loadPropertiesFile(filePath: String) {
    import java.io.File
    import java.io.FileInputStream
    val f = new File(filePath);
    if (!f.exists())
      throw new GlobalPropertiesException("No configuration file found at path [" + f.getAbsolutePath() + "]")
    println("Loading configuration file: " + f.getAbsolutePath() + ".");
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

  def prop(key: String): String = props.get(base + key).getOrElse(
    throw new GlobalPropertiesException("Property with name [%s] not found!" format base + key))

  case class GlobalPropertiesException(key: String) extends Exception(key)

}