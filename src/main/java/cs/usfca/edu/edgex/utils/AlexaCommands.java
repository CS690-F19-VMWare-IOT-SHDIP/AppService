package cs.usfca.edu.edgex.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Commands to match from Alexa Skill service. Certain Alexa devices would need to talk to Slack as well.
 */
public class AlexaCommands {
	public static String FAN = "FAN_ON";
	public static String LED_OFF = "SWITCH_OFF_LED";
	public static String LED_ON = "SWITCH_ON_LED";
	public static String LED_SERIES = "SWITCH_ON_LED_SERIES";
	
	public static List<String> SlackIntegrated = Arrays.asList("FAN_ON","SWITCH_ON_LED_SERIES");

}

