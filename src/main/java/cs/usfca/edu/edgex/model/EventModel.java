package cs.usfca.edu.edgex.model;
/**
 * EventModel specifies the event name and deviceID of a specific device that would need to be binded.
 * This is used to send a request to EventAPI and get back an eventID if successful.
 *
 */
public class EventModel {
	private String eventName;
	private String deviceID;
	
	public EventModel(String eventName, String deviceID) {
		this.eventName = eventName;
		this.deviceID = deviceID;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
	public String getEventName() {
		return this.eventName;
	}
	
	public String getDeviceID() {
		return this.deviceID;
	}

}
