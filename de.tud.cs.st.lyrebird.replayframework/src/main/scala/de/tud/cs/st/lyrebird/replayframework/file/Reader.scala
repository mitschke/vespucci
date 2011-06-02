package de.tud.cs.st.lyrebird.replayframework.file
import java.io.File

import scala.collection.immutable.SortedMap
import scala.collection.mutable.Map
import de.tud.cs.st.lyrebird.replayframework._

/**
 * Reads the output dir of Lyrebird.Recorder
 * and group the recorded events in EventSets
 * @param location : default packages folder in the output directory of Lyrebird.Recorder
 * IMPORTANT: location must be the folder of the default packages
 * @author Malte V
 */
class Reader(val location : File) {
    private var previousEvents = Map[String, Event]()
    val SEPARATOR = "_"
  
    /**
     * @return : list with all EventSets in the given location (constructor)
     * IMPORTANT every method call will reprocess the whole directory
     */
    def getAllEventSets() : List[EventSet] = {
        var res = List[EventSet]()
        getAllFilesGroupedByEventTime(location).foreach(x => res = eventsToEventSet(x) :: res)
        res
    }

    /**
     * Converts a list of Events into one EventSet
     */
    private def eventsToEventSet(eventSet : List[Event]) : EventSet = {
        new EventSet(eventSet)
    }

    /**
     * Returns all files in a dir and all sub dirs converted into Events,
     * grouped by the event time as a List of Lists 
     * ONLY PUBLIY FOR TESTING
     */
    //TODO remove currentLocation : File and use class val location instead
    def getAllFilesGroupedByEventTime(currentLocation : File) = {
        previousEvents = Map[String, Event]()
        var list = List[List[Event]]()
        var sortedFiles = getAllFilesSortedByEventTime(currentLocation)
        var lastFile : Option[Event] = None
        var subList = List[Event]()
        for (eventFile <- sortedFiles) {
            lastFile match {
                case None => {
                    subList = List[Event]()
                    subList = eventFile :: subList
                    lastFile = Some(eventFile)
                }
                case Some(x) => {
                    if (x.eventTime == eventFile.eventTime) {
                        subList = eventFile :: subList
                    } else {
                        list = subList :: list
                        subList = List[Event]()
                        subList = eventFile :: subList
                        lastFile = Some(eventFile)
                    }
                }

            }

        }
        subList :: list

    }

    /**
     * @return: a list with all Events in a directory and all sub directories
     * sorted by the event time
     */
    private def getAllFilesSortedByEventTime(currentLocation : File) : Array[Event] = {
        var allEvents = readAllFiles(currentLocation)
        val sorted = scala.util.Sorting.stableSort(allEvents, (f : Event) => f.eventTime)
        sorted
        sorted.reverse // (ascending order)
    }

    /**
     * reads all files in a directory and all sub directories
     * @param currentLocation : the start directory
     * @return a List with all files converted into Events
     */
    private def readAllFiles(currentLocation : File) : List[Event] = {
        var list = List[Event]()
        if (currentLocation.isDirectory) {
            for (file <- currentLocation.listFiles) {
                list = readAllFiles(file) ::: list
            }
        } else {
            if (checkFile(currentLocation))
                list = fileToEvent(currentLocation) :: list
        }
        list
    }

    /**
     * simple validation check
     */
    private def checkFile(file : File) : Boolean = {
        if (file.isDirectory)
            false
        if (!file.getName().endsWith("class"))
            false
        if(file.getName.split(SEPARATOR).size < 3)
            false
        true
    }

    /**
     * Converts a file into an Event
     * Only call this method if checkFile() returns true for a file
     */
    private def fileToEvent(file : File) : Event = {
        //"calc" resolved full class name (package/subpackage/.../className 
        val loc = location.getCanonicalPath
        val dest = file.getParentFile.getCanonicalPath()
        var packages = dest.drop(loc.length).replace(File.separator, "/")
        if (packages.length > 1)
            packages = packages.drop(1) + "/"

        val fileNameParts = file.getName().split(SEPARATOR)
        val resolvedName = packages + fileNameParts.drop(2).mkString.dropRight(6)
        val previousEvent = previousEvents.get(resolvedName)
        val eventType = fileNameParts(1) match {
            case "ADDED" => EventType.ADDED
            case "CHANGED" => EventType.CHANGED
            case "REMOVED" => EventType.REMOVED
        }
        val res = previousEvent match {
            case Some(x) => new Event(eventType, fileNameParts(0).toLong, resolvedName, file, Some(x))
            case _       => new Event(eventType, fileNameParts(0).toLong, resolvedName, file, None)
        }
        previousEvents.put(resolvedName, res)
        res

    }

}
