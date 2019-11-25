package cs.usfca.edu.edgex.model;

public class SlackInput extends Model {
	/*
	 * channel ID of the slack channel
	 */
	private String channel;	
	/*
	 * valid auth token for the slack request
	 */
	private String token; 		
	/*
	 * command that we expect to receive from slack to activate this device's event
	 */
	private String command;	
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
}
