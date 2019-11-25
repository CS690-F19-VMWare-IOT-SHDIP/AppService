package cs.usfca.edu.edgex.model;

import java.util.List;

/**
 * This class holds the response that comes from Slack API calls
 *
 */
public class SlackResponse {
	
	private Boolean ok;
	private List<Message> messages;
	private Boolean has_more;
	private Integer pin_count;
	private Metadata response_metadata;
	private String error;

	public Boolean getOk() {
		return ok;
	}

	public Boolean getHasMore() {
		return has_more;
	}
	
	public Integer getPinCount() {
		return pin_count;
	}
	
	public Metadata getMetaData() {
		return response_metadata;
	}
	
	public String getError() {
		return error;
	}

	public List<Message> getMessages() {
		return messages;
	}
	
	public Message getMessage() {
		if(messages.size() == 0)
			return null;
		return messages.get(0);
	}
	
	public String getMessageText() {
		if(messages.size() == 0)
			return null;
		return messages.get(0).getText();
	}
	
	public String getTs() {
		if(messages.size() == 0)
			return "0";
		return messages.get(0).getTs();
	}
	
	private class Message
	{
		private String type;
		private String user;
		private String text;
		private String ts;
		public String getText() {
			return text;
		}
		public String getUser() {
			return user;
		}
		public String getType() {
			return type;
		}
		public String getTs() {
			return ts;
		}
		
	}
	
	private class Metadata
	{
		private String next_cursor;
		public String getNextCursor() {
			return next_cursor;
		}
	}
}
