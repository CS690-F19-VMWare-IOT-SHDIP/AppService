package cs.usfca.edu.edgex.apis.eventapis;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.device.VirtualRandomModDevice;
import cs.usfca.edu.edgex.event.Event;

@Controller
public class EventAPI {
	
	@GetMapping("/listAllEvents")
    @ResponseBody()
	public Set<String> listAllEvents() {
		return EventHandlers.getListOfEvents();
	}
	
	@GetMapping("/getEvent")
    @ResponseBody()
	public Event getEvent() throws NoSuchMethodException, SecurityException, 
		InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return EventHandlers.getObjectForEvent("RandomModEvent", new VirtualRandomModDevice(null));
	}
	
	@GetMapping("/listEventForType/{deviceType}")
    @ResponseBody()
	public Set<String> listEventForType(@PathVariable(value = "deviceType") DeviceType deviceType) 
				throws NoSuchMethodException, SecurityException, IllegalAccessException, 
					IllegalArgumentException, InvocationTargetException  {
		return EventHandlers.listEventsWithType(deviceType);
	}
}
