package cs.usfca.edu.edgex.edgexclient;

import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.utils.URLPaths;

public class LEDClient {
	
	private static Request request = new Request();
	
	public static String get(DeviceModel deviceModel) {
		String urlPath = URLPaths.DEVICE_EVENT.replaceAll("\\{\\}", deviceModel.getDeviceID()).replaceAll("\\[\\]", deviceModel.getResourceName());
		URL url = request.url(URLPaths.HOSTANME, urlPath);
		HttpURLConnection con = request.connect(url, "GET");
		
		String response = request.getResponse(con);
		if (response != null) {
			DeviceEvent dEvent = new Gson().fromJson(response.toString(), DeviceEvent.class);
			System.out.println("Data : " + dEvent.getDeviceName() + " : " + dEvent.getReadings().get(0).getResourceName() + " : " + dEvent.getReadings().get(0).getValue());
			return dEvent.getReadings().get(0).getValue();
		}
		con.disconnect();
		return null;
	}
}
