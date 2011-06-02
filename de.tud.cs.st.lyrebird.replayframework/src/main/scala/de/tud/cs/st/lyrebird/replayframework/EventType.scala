package de.tud.cs.st.lyrebird.replayframework

/**
 * 
 * @author Malte V
 */
object EventType extends Enumeration {
    val CHANGED = Value("CHANGED")
    val REMOVED = Value("REMOVED")
    val ADDED = Value("ADDED")
}