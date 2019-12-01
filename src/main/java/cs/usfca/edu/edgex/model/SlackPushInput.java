package cs.usfca.edu.edgex.model;

public class SlackPushInput {
	private String token;
	private String channel;
	private String text;
	
	public SlackPushInput(String token, String channelID, String text) {
		this.token = token;
		this.channel = channelID;
		this.text = text;
	}

}
