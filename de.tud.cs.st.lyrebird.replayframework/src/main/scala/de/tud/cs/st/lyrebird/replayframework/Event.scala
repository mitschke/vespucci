package de.tud.cs.st.lyrebird.replayframework
import java.io.File




/**
 * Save one Event. 
 * A event consists of
 * 	EventType: @see EventType
 * 	EventTime: ..
 * 	ResolvedClassName: package/subpackage/.../ClassName
 * 	previousEvent: if Lyrebird recorded an event to the current classFile before this event, previousEvent saves a link to the last event.
 * 					else previousEvent = None
 * IMPORTENT: it is possible to get a remove event WITHOUT a previous add / change Event.
 */
case class Event(val eventType : EventType.Value,
                 val eventTime : Long,
                 val resolvedClassName : String,
                 val eventFile : File,
                 val previousEvent : Option[Event]) {
    // resolvedClassName := package/subpackage/ClassName
    def getCorrespondingEvent() : Option[Event] = {
        previousEvent
    }
}
