package apis;

public class AlexaModel {
	
	private String messageType;
	private float timestamp;
	
	public AlexaModel(String messageType, float timestamp) {
		this.messageType = messageType;
		this.timestamp = timestamp;
	}
	
	public String getMessageType() {
		return this.messageType;
	}
	
	public float getTimestamp() {
		return this.timestamp;
	}

}
