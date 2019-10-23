package cs.usfca.edu.edgex.apis.eventapis;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import cs.usfca.edu.edgex.apis.deviceapis.DeviceHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.device.VirtualRandomModDevice;
import cs.usfca.edu.edgex.event.Event;
import cs.usfca.edu.edgex.model.DeviceModel;

@Controller
@RequestMapping("/events")
public class EventAPI {
	
	@GetMapping("/listAllEventTypes")
    @ResponseBody()
	public Set<String> listAllEventTypes() {
		return EventHandlers.getListOfEventTypes();
	}
	
	
	@GetMapping("/bindEvent/{eventName}/{deviceID}")
    @ResponseBody()
	public String getEvent(@PathVariable(value="eventName") String eventName, @PathVariable(value="deviceID") String deviceID) throws NoSuchMethodException, SecurityException, 
		InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("*** " + eventName + " *** " + deviceID);
		Device<?> device = EventHandlers.getDeviceFromMap(deviceID);
		if(device != null) {
			Set<String> eventsWithDevice = EventHandlers.listEventsWithType(device.getDeviceType());
			if(eventsWithDevice.contains(eventName)) {
				return EventHandlers.bindEventToDevice(eventName, device);
			}
		}
		return "NULL";
	}
	
	@GetMapping("/getBindEventsToDevice")
    @ResponseBody()
	public HashMap<String, Event> getEventsToDeviceMap() {
		return EventHandlers.getBindEventsToDeviceMap();
	}
	
	@GetMapping("/deleteEventID/{eventID}")
    @ResponseBody()
	public boolean deleteEventID(@PathVariable(value="eventID") String eventID) {
		return EventHandlers.removeEventID(eventID);
	}
	
	@GetMapping("/listEventForType/{deviceType}")
    @ResponseBody()
	public Set<String> listEventForType(@PathVariable(value = "deviceType") DeviceType deviceType) 
				throws NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException  {
		
		Set<String> eventsForType;
		try {
			eventsForType = EventHandlers.listEventsWithType(deviceType);
			return eventsForType;
		} 
		catch (NoSuchMethodException e) {
			String errMsg = String.format("Events for device-type : %s not found", deviceType);
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, errMsg, null);
		}
	}
}
