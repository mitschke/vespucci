package de.tud.cs.st.lyrebird.replayframework
import java.io.File
import de.tud.cs.st.lyrebird.replayframework.file.Reader


/**
 * That class allows to replay the recorded events by Lyrebird.recorder
 * Recorded changes are grouped in EventSets 
 * @author Malte V
 */
class Replay(val location: File) {
	val reader = new Reader(location)
	
	    
    /**
     * IMPORTANT every method call will reprocess the whole directory
     */
    def foreach(f : EventSet => _) : Unit = {
        reader.getAllEventSets.foreach(x => f(x))
    }
    /**
     * @return : list with all EventSets in the given location (constructor)
     * IMPORTANT every method call will reprocess the whole directory
     */
    def getAllEventSets() : List[EventSet] = {
       reader.getAllEventSets
    }
}