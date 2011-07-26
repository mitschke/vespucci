/* License (BSD Style License):
 * Copyright (c) 2011
 * Department of Computer Science
 * Technische Universität Darmstadt
 * All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *  - Neither the name of the Software Technology Group or Technische 
 *    Universität Darmstadt nor the names of its contributors may be used to 
 *    endorse or promote products derived from this software without specific 
 *    prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
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
     * the list is ascendet ordered by Event.eventTime
     */
    def getAllEventSets() : List[EventSet] = {
        var res = List[EventSet]()
        getAllFilesGroupedByEventTime(location).foreach(x => res = eventsToEventSet(x) :: res)
        res
    }

    /**
     * Converts a list of Events into one EventSet
     * All Events must have the same eventTime
     */
    private def eventsToEventSet(eventSet : List[Event]) : EventSet = {
        new EventSet(eventSet)
    }

    /**
     * Returns a list of list of Events grouped by the eventTime and descending ordered by time
     * the list of Events contains all files in the given directory and all sub directories converted into Events,
     *
     * ONLY PUBLIY FOR TESTING
     */
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
     * sorted by the eventTime
     */
    private def getAllFilesSortedByEventTime(currentLocation : File) : Array[Event] = {
        var allEvents = readAllFiles(currentLocation)
        val sorted = scala.util.Sorting.stableSort(allEvents, (f : Event) => f.eventTime)
        sorted
        //sorted.reverse // (ascending order)
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
        if (file.getName.split(SEPARATOR).size < 3)
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
            case "ADDED"   => EventType.ADDED
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
