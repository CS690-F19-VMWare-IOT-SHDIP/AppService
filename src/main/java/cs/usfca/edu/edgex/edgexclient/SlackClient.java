package cs.usfca.edu.edgex.edgexclient;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import cs.usfca.edu.edgex.model.SlackInput;
import cs.usfca.edu.edgex.model.SlackPushInput;
import cs.usfca.edu.edgex.model.SlackResponse;
import cs.usfca.edu.edgex.utils.URLPaths;

public class SlackClient {
	private static Request request = new Request();
	
	/**
	 * Gets the oldest message from the specified slack channel
	 * @param token
	 * @param channelID
	 * @param oldestTimeStamp
	 * @return
	 */
	public SlackResponse get(String token, String channelID, String oldestTimeStamp)
	{
		String urlStr = URLPaths.GET_OLDEST_SLACK_MSG.replaceAll("YOUR_TOKEN_HERE", token)
				.replaceAll("CONVERSATION_ID_HERE", channelID).replaceAll("OLDEST_TIMESTAMP_HERE", oldestTimeStamp);
		URL url = null;
		SlackResponse result = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			System.out.println("Bad url in SlackResponse.get()");
			e.printStackTrace();
			return result;
		}
		System.out.println("Slack API Request Url:: " + urlStr);
		String contentType = "application/json";
		String authorization = "Bearer " + token;
		HttpURLConnection con = request.connect(url, "GET", contentType, authorization);
		String response = request.getResponse(con);
		result = new Gson().fromJson(response.toString(), SlackResponse.class);
		return result;
	}
	
	public SlackResponse post(String token, String channelID, String message) {
		String urlStr = URLPaths.POST_SLACK_MESSAGE;

		SlackPushInput input = new SlackPushInput(token, channelID, message);
		URL url = null;
		SlackResponse result = null;
		try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			System.out.println("Bad url in SlackResponse.get()");
			e.printStackTrace();
			return result;
		}
		System.out.println("Slack API Request Url:: " + urlStr);
		String contentType = "application/json";
		String authorization = "Bearer " + token;
		HttpURLConnection con = request.connect(url, "POST", contentType, authorization);
		
		if(request.writeToBody(con,  new Gson().toJson(input, SlackPushInput.class))) {
			String response = request.getResponse(con);
			result = new Gson().fromJson(response.toString(), SlackResponse.class);
			return result;
		}

		return null;
	}
}
