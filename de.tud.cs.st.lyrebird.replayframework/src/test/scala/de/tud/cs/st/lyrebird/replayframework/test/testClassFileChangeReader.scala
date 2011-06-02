package de.tud.cs.st.lyrebird.replayframework

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import java.io.File
import de.tud.cs.st.lyrebird.replayframework._
import de.tud.cs.st.lyrebird.replayframework.file._
import java.net.URL
class TestClassFileChangeReader extends org.scalatest.junit.JUnitSuite {


    val location = new File("./src/test/recources/smallTestSet")
    
    @Test
    def readAndGroupSomeTestData() {

        //TODO what is the maven way for doing something like this
        // FIXME
        // solution 1: read from a resource in the classpath (as done in the sae tests)
        // solution 2: use maven copy resources plugin and encode path as "target/choice-for-copied-resources    
        val reader = new Reader(location)     
        var res : List[List[Event]] = reader.getAllFilesGroupedByEventTime(location)
        var lastEventTime : Long = 0
        var sumEvents = 0
        var sumEventFiles = 0
        res.foreach(x => {
            //check that the event time increase with every event
            assertTrue(x.head.eventTime > lastEventTime)
            lastEventTime = x.head.eventTime
            sumEvents += 1
            x.foreach(y => {
                //check that the eventTime is the same for all eventfiles in one event
                assertTrue(y.eventTime == lastEventTime)
                sumEventFiles += 1
            })
        })
        assertTrue(sumEvents == 6)
        assertTrue(sumEventFiles == 85)

    }
    @Test
    def applyFonAllEvents() {
        val reader = new Replay(location)
        reader.foreach(println _)
    }

}