package cs.usfca.edu.edgex.apis.eventapis;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;

import cs.usfca.edu.edgex.model.ErrorModel;
/**
 * All calls made to /events are processed from here.
 */
@Controller
@RequestMapping("/events")
public class EventAPI {
	
	/**
	 * All different event types supported.
	 * @return ResponseEntity<?>
	 */
	@GetMapping("/listAllEventTypes")
    @ResponseBody()
	public ResponseEntity<?> listAllEventTypes() {
		return ResponseEntity.status(HttpStatus.OK).body(EventHandlers.getListOfEventTypes());
	}
	
	/**
	 * Check if given deviceID and eventName is valid, else throw concerned errors.
	 * Bind an event to device if possible.
	 * @param eventName
	 * @param deviceID
	 * @return ResponseEntity<?>
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@GetMapping("/bindEvent/{eventName}/{deviceID}")
    @ResponseBody()
	public ResponseEntity<?> getEvent(@PathVariable(value="eventName") String eventName, @PathVariable(value="deviceID") String deviceID) throws NoSuchMethodException, SecurityException, 
		InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		System.out.println("*** " + eventName + " *** " + deviceID);
		Device<?> device = EventHandlers.getDeviceFromMap(deviceID);
		if(device != null) {
			Set<String> eventsWithDevice = EventHandlers.listEventsWithType(device.getDeviceType());
			if(eventsWithDevice.contains(eventName)) {
				return ResponseEntity.status(HttpStatus.OK).body(EventHandlers.bindEventToDevice(eventName, device));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorModel("Invalid eventName, cannot bind"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorModel("Invalid deviceID, cannot bind"));
	}
	
	/**
	 * Show list of all events binded to certain devices.
	 * @return ResponseEntity<?>
	 */
	@GetMapping("/eventIDList")
    @ResponseBody()
	public ResponseEntity<?> getEventsToDeviceMap() {
		return ResponseEntity.status(HttpStatus.OK).body(EventHandlers.getEvents());
	}
	
	/**
	 * Remove an eventID from map if not needed anymore.
	 * @param eventID
	 * @return ResponseEntity<?>
	 */
	@GetMapping("/deleteEventID/{eventID}")
    @ResponseBody()
	public ResponseEntity<?> deleteEventID(@PathVariable(value="eventID") String eventID) {
		return ResponseEntity.status(HttpStatus.OK).body(EventHandlers.removeEventID(eventID));
	}
	
	/**
	 * Show all events supported for a specific device type.
	 * @param deviceType
	 * @return ResponseEntity<?>
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@GetMapping("/listEventForType/{deviceType}")
    @ResponseBody()
	public ResponseEntity<?> listEventForType(@PathVariable(value = "deviceType") DeviceType deviceType) 
				throws NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException  {
		Set<String> eventsForType;
		try {
			eventsForType = EventHandlers.listEventsWithType(deviceType);
			return ResponseEntity.status(HttpStatus.OK).body(eventsForType);
		} 
		catch (NoSuchMethodException e) {
			String errMsg = String.format("Events for device-type : %s not found", deviceType);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorModel(errMsg));
		}
	}
	
}
