package cs.usfca.edu.edgex.edgexclient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * App Service's endpoint for Alexa Skill to push data down.
 */
@Controller
@RequestMapping("/alexa")
public class AlexaClient {
	
	public static HashMap<String, Float> alexaMessages = new HashMap<>();
	
	@PostMapping(value = "/latestIntentMessage/{messageType}/{timestamp}")
	@ResponseBody()
	public ResponseEntity<?> updateIntentMessage(@PathVariable("messageType") String messageType, @PathVariable("timestamp") float timestamp) {
		alexaMessages.put(messageType, timestamp);
		for(Map.Entry<String, Float> items : alexaMessages.entrySet()) {
			System.out.println(items.getKey() + " : " + items.getValue());
		}
		return ResponseEntity.status(HttpStatus.OK).body("success");
	}
	
	/**
	 * For given message type, retrieve timestamp of latest message
	 */
	public static float getTimeStamp(String messageType) {
		if (alexaMessages.containsKey(messageType)) {
			return alexaMessages.remove(messageType);
			
		}
		return 0;
	}

}
