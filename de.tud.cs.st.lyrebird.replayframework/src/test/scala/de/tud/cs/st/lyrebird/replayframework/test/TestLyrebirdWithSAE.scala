package sae.test;

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import sae.reader._
import sae.bytecode._
import java.io.File
import sae.Observer
import de.tud.cs.st.bat.ObjectType

class TestLyrebirdWithSAE extends org.scalatest.junit.JUnitSuite {
    class TestObserver extends Observer[ObjectType] {
        /*
         *Test setup
        //no update should be sent
        (Event(REMOVED,1306693159489,de/tud/cs/se/flashcards/Main,.\src\main\recources\eventTestSet\changes\de\tud\cs\se\flashcards\1306693159489_REMOVED_Main.class,None)))
		// state == 0 && added
		(Event(ADDED,1306693160432,de/tud/cs/se/flashcards/MacOSXAdapter,.\src\main\recources\eventTestSet\changes\de\tud\cs\se\flashcards\1306693160432_ADDED_MacOSXAdapter.class,None)))
		//state == 1 && added
		(Event(ADDED,1306693160433,de/tud/cs/se/flashcards/Main,.\src\main\recources\eventTestSet\changes\de\tud\cs\se\flashcards\1306693160433_ADDED_Main.class,Some(...)))))
		//state == 2 && changed
		(Event(CHANGED,1306693301544,de/tud/cs/se/flashcards/Main,.\src\main\recources\eventTestSet\changes\de\tud\cs\se\flashcards\1306693301544_CHANGED_Main.class,Some(...))))
		//state == 3 && added
		(Event(ADDED,1306693311363,de/tud/cs/se/flashcards/AddedClass,.\src\main\recources\eventTestSet\changes\de\tud\cs\se\flashcards\1306693311363_ADDED_AddedClass.class,None)))
		//state == 4 && changed
		(Event(CHANGED,1306693408683,de/tud/cs/se/flashcards/MacOSXAdapter,.\src\main\recources\eventTestSet\changes\de\tud\cs\se\flashcards\1306693408683_CHANGED_MacOSXAdapter.class,Some(...))))
		//state == 5 && changed
		(Event(CHANGED,1306693408684,de/tud/cs/se/flashcards/Main,.\src\main\recources\eventTestSet\changes\de\tud\cs\se\flashcards\1306693408684_CHANGED_Main.class,Some(...)))))
		//state == 6 && removed && with last file
		(Event(REMOVED,1306693411749,de/tud/cs/se/flashcards/MacOSXAdapter,.\src\main\recources\eventTestSet\changes\de\tud\cs\se\flashcards\1306693411749_REMOVED_MacOSXAdapter.class,Some(...)))))
		//state == 7 && removed && with last file
		(Event(REMOVED,1306693411750,de/tud/cs/se/flashcards/Main,.\src\main\recources\eventTestSet\changes\de\tud\cs\se\flashcards\1306693411750_REMOVED_Main.class,Some(...)))))

         */
        var state = 0
        def updated(oldV : ObjectType, newV : ObjectType) : Unit = {
            // for the time being we do not generate update events, but remove and add the classes
            state match {
                case _ => fail()
            }
        }

        def removed(v : ObjectType) : Unit = {
            state match {
                case 2 =>
                    assertTrue(v.toJava.toLowerCase.contains("main"))
                    state += 1
                case 5 =>
                    assertTrue(v.toJava.toLowerCase.contains("macosxadapter"))
                    state += 1
                case 7 =>
                    assertTrue(v.toJava.toLowerCase.contains("main"))
                    state += 1
                case 9 =>
                    assertTrue(v.toJava.toLowerCase.contains("macosxadapter"))
                    state += 1
                case 10 =>
                    assertTrue(v.toJava.toLowerCase.contains("main"))
                    state += 1
                case _ => fail()
            }
        }

        def added(v : ObjectType) : Unit = {
            state match {
                case 0 =>
                    assertTrue(v.toJava.toLowerCase.contains("macosxadapter"))
                    state += 1
                case 1 =>
                    assertTrue(v.toJava.toLowerCase.contains("main"))
                    state += 1
                case 3 =>
                    assertTrue(v.toJava.toLowerCase.contains("main"))
                    state += 1
                case 4 =>
                    assertTrue(v.toJava.toLowerCase.contains("addedclass"))
                    state += 1
                case 6 =>
                    assertTrue(v.toJava.toLowerCase.contains("macosxadapter"))
                    state += 1
                case 8 =>
                    assertTrue(v.toJava.toLowerCase.contains("main"))
                    state += 1
                case _ => fail()
            }
        }
    }
    private var db : BytecodeDatabase = new BytecodeDatabase()
    private val location = new File("./src/main/recources/eventTestSet/changes")
    //private val resourceName = "initBytecodeChangeEventTest.jar"


    @Test
    def processEventSets() : Unit = {

//        //val reader = new ClassFileChangeEventReader(location)
//        val replay = new de.tud.cs.st.lyrebird.replayframework.Replay(location)
//        val op = new TestObserver()
//        db.classfiles.addObserver(op)
//        //reader.foreach(println _)
//        replay.processAllEventSets(db.getAddClassFileFunction, db.getRemoveClassFileFunction)
//        assertTrue(op.state == 11)

    }

}
