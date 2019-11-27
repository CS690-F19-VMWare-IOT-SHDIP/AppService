package cs.usfca.edu.edgex.utils;

public class URLPaths {
	public static String HOSTANME = "http://10.0.0.150";
	
	public static String DEVICE_SERVICE_PORT = "49992";
	public static String COMMAND_SERVICE_PORT = "48082";
	public static String CORE_DATA_PORT = "48080";
	public static String METADATA = "48081";
	
	public static String DEVICE_EVENT  = DEVICE_SERVICE_PORT + "/api/v1/device/{}/[]";
	public static String GET_DEVICE_INFO = COMMAND_SERVICE_PORT + "48082/api/v1/device/name/{}";
	public static String GET_EVENT_DEVICE = CORE_DATA_PORT + "/api/v1/event/device/{}/1";
	public static String PUT_READING = CORE_DATA_PORT + "/api/v1/event";
	public static String GET_DEVICE_META = METADATA + "/api/v1/device/name/{deviceName}";
	
	public static String GET_OLDEST_SLACK_MSG = "https://slack.com/api/conversations.history?"
			+ "token=YOUR_TOKEN_HERE&channel=CONVERSATION_ID_HERE&oldest=OLDEST_TIMESTAMP_HERE&limit=1";
}
