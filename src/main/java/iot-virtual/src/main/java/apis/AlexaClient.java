package apis;

import java.net.HttpURLConnection;
import java.net.URL;
import apis.Request;

public class AlexaClient {
	private static Request request = new Request();
	
	public static String post(AlexaModel alexaModel) {
		URL url = request.url(Utils.URL + "/" + alexaModel.getMessageType() + "/" + String.valueOf(alexaModel.getTimestamp()));
		HttpURLConnection con = request.connect(url, "POST");
		String response = request.getResponse(con);

		if(response != null) {
			return response;
		}
		con.disconnect();
		return null;
	}

}
