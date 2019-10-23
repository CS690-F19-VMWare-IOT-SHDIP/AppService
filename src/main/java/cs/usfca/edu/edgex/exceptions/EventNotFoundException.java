package cs.usfca.edu.edgex.exceptions;

public class EventNotFoundException extends Exception {
	public EventNotFoundException(String eventId) {
		super("Event with Id: " + eventId + " is not among our supported events.");
	}
}
