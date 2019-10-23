package cs.usfca.edu.edgex.model;

public class EventIDModel {
	private String eventID;
	
	public EventIDModel() {}
	
	public EventIDModel(String eventID) {
		this.eventID = eventID;
	}
	
	public void setEventID(String newEventID) {
		this.eventID = newEventID;
	}
	
	public String getEventID() {
		return this.eventID;
	}

}
