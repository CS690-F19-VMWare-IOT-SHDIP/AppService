package cs.usfca.edu.edgex.apis.eventapis;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.event.Event;

public class EventHandlers {
	private static Map<String, Class<?>> events;
	
	public static Set<String> getListOfEvents() {
		if(events == null) {
			events = new HashMap<String, Class<?>>();
			Reflections reflections = new Reflections("cs.usfca.edu.edgex");    
	    	Set<Class<? extends Event>> classes = reflections.getSubTypesOf(Event.class);
	    	for(Class<? extends Event> clazz : classes) {
	    		if(!clazz.isInterface())
	    			events.put(clazz.getSimpleName(), clazz);
	    	}
		}
		return events.keySet();
	}
	
	public static Set<String> listEventsWithType(DeviceType type) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Set<String> set = new HashSet<String>();
		if(events == null) {
			getListOfEvents();
		}
		for(String eventName : events.keySet()) {
			Method method = events.get(eventName).getMethod("getDeviceType");
			if(type == (DeviceType) method.invoke(null)) {
				set.add(eventName);
			}
		}
		return set;
	}
	
	public static Event getObjectForEvent(String eventName, Device<?> device) 
			throws NoSuchMethodException, SecurityException, InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(events == null) {
			getListOfEvents();
    	}
		Constructor<?> cons = events.get(eventName).getConstructor(Device.class);
		return (Event) cons.newInstance(device);
	}
}
