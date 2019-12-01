package cs.usfca.edu.edgex.edgexclient;

import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.utils.URLPaths;

/**
 * Client Proxy to pull and push device readings into EdgeX
 */
public class EdgeXClient {
	private static Request request = new Request();
		
	/**
	 * Get readings for a given deviceID and resourceName
	 * @param deviceModel
	 * @return String
	 */
	public static String get(DeviceModel deviceModel) {
		String urlPath = URLPaths.DEVICE_EVENT.replaceAll("\\{\\}", deviceModel.getDeviceID()).replaceAll("\\[\\]", deviceModel.getResourceName());
		URL url = request.url(URLPaths.HOSTANME, urlPath);
		HttpURLConnection con = request.connect(url, "GET");
		
		String response = request.getResponse(con);
		if (response != null) {
			DeviceEvent dEvent = new Gson().fromJson(response.toString(), DeviceEvent.class);
			System.out.println("Data : " + dEvent.getDeviceName() + " : " + dEvent.getReadings().get(0).getResourceName() + " : " + dEvent.getReadings().get(0).getValue());
			return response;
		}
		con.disconnect();
		return null;
	}
	
	/**
	 * Put or set a device's state
	 * @param deviceModel
	 * @param putBody
	 * @return String
	 */
	public static String put(DeviceModel deviceModel, String putBody) {

		String urlPath = URLPaths.DEVICE_EVENT.replaceAll("\\{\\}", deviceModel.getDeviceID()).replaceAll("\\[\\]", deviceModel.getResourceName());
		URL url = request.url(URLPaths.HOSTANME, urlPath);
		HttpURLConnection con = request.connect(url, "PUT");
		
		if(request.writeToBody(con, putBody)) {
			// TODO: Check response and convert json string to object. Check value and return boolean accordingly
			String response = request.getResponse(con);
			System.out.println("PUT RESPONSE : " + response);
		}
		con.disconnect();
		return "";
	}
	
	/**
	 * Get deviceID for a deviceName
	 * @param deviceName
	 * @return
	 */
	public static String getDeviceID(String deviceName) {
		String urlPath = URLPaths.GET_DEVICE_META.replaceAll("\\{deviceName\\}", deviceName);
		URL url = request.url(URLPaths.HOSTANME, urlPath);
		System.out.println(url);
		HttpURLConnection con = request.connect(url, "GET");
		String response = request.getResponse(con);
		con.disconnect();
		return response;
	}

}
