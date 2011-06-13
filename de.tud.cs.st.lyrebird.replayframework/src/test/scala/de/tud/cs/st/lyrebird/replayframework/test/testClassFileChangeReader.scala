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
 
        val reader = new Reader(location)     
        var res : List[List[Event]] = reader.getAllFilesGroupedByEventTime(location).reverse
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