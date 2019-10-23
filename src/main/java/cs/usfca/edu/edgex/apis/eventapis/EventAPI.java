package cs.usfca.edu.edgex.apis.eventapis;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;

import cs.usfca.edu.edgex.model.ErrorModel;
import cs.usfca.edu.edgex.model.EventIDModel;
import cs.usfca.edu.edgex.model.EventModel;
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
	@GetMapping("/list")
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
	@PostMapping(value = "/bind", consumes = "application/json")
    @ResponseBody()
	public ResponseEntity<?> getEvent(@RequestBody EventModel eventModel) throws NoSuchMethodException, SecurityException, 
		InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Device<?> device = EventHandlers.getDeviceFromMap(eventModel.getDeviceID());
		if(device != null) {
			Set<String> eventsWithDevice = EventHandlers.listEventsWithType(device.getDeviceType());
			if(eventsWithDevice.contains(eventModel.getEventName())) {
				String eventID = EventHandlers.bindEventToDevice(eventModel.getEventName(), device);
				return ResponseEntity.status(HttpStatus.OK).body( new EventIDModel(eventID));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorModel("Invalid eventName, cannot bind"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ErrorModel("Invalid deviceID, cannot bind"));
	}
	
	/**
	 * Show list of all events binded to certain devices.
	 * @return ResponseEntity<?>
	 */
	@GetMapping("/eventID")
    @ResponseBody()
	public ResponseEntity<?> getEventsToDeviceMap() {
		return ResponseEntity.status(HttpStatus.OK).body(EventHandlers.getEvents());
	}
	
	/**
	 * Remove an eventID from map if not needed anymore.
	 * @param eventID
	 * @return ResponseEntity<?>
	 */
	@PostMapping(value = "/deleteID", consumes = "application/json")
    @ResponseBody()
	public ResponseEntity<?> deleteEventID(@RequestBody EventIDModel eventIDModel) {
		return ResponseEntity.status(HttpStatus.OK).body(EventHandlers.removeEventID(eventIDModel.getEventID()));
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
	@GetMapping("/eventsForType/{deviceType}")
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
	
	@GetMapping("/eventsToDevices/{eventName}")
	@ResponseBody()
	public ResponseEntity<?> listEventsToDevices(@PathVariable(value = "eventName") String eventName) {
		return ResponseEntity.status(HttpStatus.OK).body(EventHandlers.getEventNameToDevices());
	}
	
}
