package cs.usfca.edu.edgex.model;
/**
 * Model to receive data from Alexa skill via AlexaClient.
 * messageType defines the device and timestamp relates to the last message received for the messageType
 */
public class AlexaModel extends Model {
	
	private String messageType;
	private float timestamp;
	
//	public AlexaModel() {}
	
//	public AlexaModel(String messageType) {
//		this.messageType = messageType;
//		this.timestamp = 0;
//	}
	
	public String getMessageType() {
		return this.messageType;
	}
	
	public float getTimestamp() {
		return this.timestamp;
	}
	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	public void setTimeStamp(float timestamp) {
		this.timestamp = timestamp;
	}

}
