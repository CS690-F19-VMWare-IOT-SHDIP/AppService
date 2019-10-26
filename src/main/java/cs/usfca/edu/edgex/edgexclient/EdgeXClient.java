package cs.usfca.edu.edgex.edgexclient;

import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import cs.usfca.edu.edgex.model.DeviceModel;

public class EdgeXClient {
	private static Request request = new Request();
		
	
	public static String get(DeviceModel deviceModel, String deviceID) {
		String urlPath = URLPaths.DEVICE_EVENT.replaceAll("\\{\\}", deviceID).replaceAll("\\[\\]", deviceModel.getResourceName());
		URL url = request.url(URLPaths.HOSTANME, urlPath);
		HttpURLConnection con = request.connect(url, "GET");
		
		String response = request.getResponse(con);
		if (response != null) {
			DeviceEvent[] dEvent = new Gson().fromJson(response.toString(), DeviceEvent[].class);
			System.out.println("Data : " + dEvent[0].getDeviceName() + " : " + dEvent[0].getReadings().get(0).getResourceName() + " : " + dEvent[0].getReadings().get(0).getValue());
			return response;
		} 
		return null;
	}
	
	public static String put(DeviceModel deviceModel, String deviceID, String putBody) {
		
		System.out.println("** " + putBody);
		
		String urlPath = URLPaths.DEVICE_EVENT.replaceAll("\\{\\}", deviceID).replaceAll("\\[\\]", deviceModel.getResourceName());
		URL url = request.url(URLPaths.HOSTANME, urlPath);
		HttpURLConnection con = request.connect(url, "PUT");
		
		if(request.writeToBody(con, putBody)) {
			String response = request.getResponse(con);
			System.out.println("PUT RESPONSE : " + response);
		}
		
		return "";
	}
	

	

	
	

}
