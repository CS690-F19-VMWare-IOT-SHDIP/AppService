package cs.usfca.edu.edgex.apis.eventapis;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.reflections.Reflections;

import cs.usfca.edu.edgex.apis.deviceapis.DeviceHandlers;
import cs.usfca.edu.edgex.apis.deviceapis.VirtualDeviceHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.device.physicaldevices.PhysicalDevice;
import cs.usfca.edu.edgex.event.Event;

public class EventHandlers {
	private static Map<String, Class<?>> supportedEvents;
	public static HashMap<String, Event> bindEventsToDeviceMap = new HashMap<String, Event>();
	public static HashMap<String, ArrayList<Device<?>>> eventNameToDevices = new HashMap<String, ArrayList<Device<?>>>();
	
	/*
	 * Show all supported events from packages : cs.usfca.edu.edgex.event of class type Event.
	 */
	public static Set<String> getListOfEventTypes() {
		if(supportedEvents == null) {
			supportedEvents = new HashMap<String, Class<?>>();
			Reflections reflections = new Reflections("cs.usfca.edu.edgex.event");    
	    	Set<Class<? extends Event>> classes = reflections.getSubTypesOf(Event.class);
	    	for(Class<? extends Event> clazz : classes) {
	    		if(!clazz.isInterface())
	    			supportedEvents.put(clazz.getSimpleName(), clazz);
	    	}
		}
		return supportedEvents.keySet();
	}
	
	
	/*
	 * List events with pertaining to specific device-type.
	 */
	public static Set<String> listEventsWithType(DeviceType type) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Set<String> set = new HashSet<String>();
		if(supportedEvents == null) {
			getListOfEventTypes();
		}
		for(String eventName : supportedEvents.keySet()) {
			Method method = supportedEvents.get(eventName).getMethod("getDeviceType");
			if(type == (DeviceType) method.invoke(null)) {
				set.add(eventName);
			}
		}
		return set;
	}
	
	/*
	 * Given an event name and device, check if concerned event class can be binded with the given device.
	 * If success, check if such and eventID already present or not and then return an eventID
	 */
	public static String bindEventToDevice(String eventName, Device<?> device) 
			throws NoSuchMethodException, SecurityException, InstantiationException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(supportedEvents == null) {
			getListOfEventTypes();
    	}
		
		updateEventNameToDevices(eventName, device);
		
		Constructor<?> cons = supportedEvents.get(eventName).getConstructor(Device.class);
		Event eventToDevice = (Event) cons.newInstance(device);
		
		String eventID = checkBindMap(eventToDevice);
		if(eventID != "") {
			return eventID;
		}
		eventID = addToBindMap(eventToDevice);	
		return eventID;
	}
	
	public static HashMap<String, Event> getBindEventsToDeviceMap() {
		return bindEventsToDeviceMap;
	}
	
	/*
	 * Return all event names that are binded to a specific device.
	 */
	public static ArrayList<String> getEventNamesForDevice(Device<?> device) {
		ArrayList<String> eventNames = new ArrayList<>();
		for(Map.Entry<String, ArrayList<Device<?>>> entry : eventNameToDevices.entrySet()) {
			for(Device<?> eventDevice : entry.getValue()) {
				if(eventDevice.equals(device)) {
					eventNames.add(entry.getKey());
				}
			}
		}
		return eventNames;
	}
	
	/*
	 * Read through bindEventsToDeviceMap to check if eventID is created or not for an Event
	 */
	public static String checkBindMap(Event eventToDevice) {
		for(Map.Entry<String, Event> entry : bindEventsToDeviceMap.entrySet()) {
			System.out.println("<< " + entry.getValue().compareTo(eventToDevice));
			if(entry.getValue().compareTo(eventToDevice) == 0) { 
				return entry.getKey();
			}
		}
		return "";
	}
	
	/*
	 * Add new event binded to a device into map and return it's ID
	 */
	public static String addToBindMap(Event eventToDevice) {
		String eventID = UUID.randomUUID().toString();
		bindEventsToDeviceMap.put(eventID, eventToDevice);
		return eventID;
	}
	
	/*
	 * Update map of eventName --> List of devices
	 */
	public static void updateEventNameToDevices(String eventName, Device<?> device) {
		if(eventNameToDevices.containsKey(eventName)) {
			System.out.println("APPEDNING : " + device);
			eventNameToDevices.get(eventName).add(device);
		}
		else {
			System.out.println("ADDING : " + device);
			eventNameToDevices.put(eventName, new ArrayList<Device<?>>());
			eventNameToDevices.get(eventName).add(device);
		}	
		for(Map.Entry<String, ArrayList<Device<?>>> entry : eventNameToDevices.entrySet()) {
			System.out.print("\n" + entry.getKey() + " -- > ");
			for(Device<?> d : entry.getValue()) {
				System.out.print(d + ", ");
			}
		}
	}
	
	/*
	 * Remove a specific event binded to a device using eventID
	 */
	public static boolean removeEventID(String eventID) {
		if(bindEventsToDeviceMap.containsKey(eventID)) {
			bindEventsToDeviceMap.remove(eventID);
			return true;
		}
		return false;
	}
	
	public static boolean removeDeviceFromEventName(String eventName, Device<?> device) {
		if(eventNameToDevices.containsKey(eventName)) {
			ArrayList<Device<?>> deviceList = eventNameToDevices.get(eventName);
			deviceList.remove(device);
			eventNameToDevices.put(eventName, deviceList);
			return true;
		}
		return false;
	}
	
	public static Device<?> getDeviceFromMap(String deviceID) {
		HashMap<String, PhysicalDevice<?>> physicalDeviceMap = (HashMap<String, PhysicalDevice<?>>) DeviceHandlers.getPhysicalDevices();
		HashMap<String, Device<?>> virtualDeviceMap = (HashMap<String, Device<?>>) VirtualDeviceHandlers.getVirtualDevices();

		for(Map.Entry<String, PhysicalDevice<?>> entry : physicalDeviceMap.entrySet()) {
			if(entry.getKey().equals(deviceID)) {
				return entry.getValue();
			}
		}
		
		for(Map.Entry<String, Device<?>> entry : virtualDeviceMap.entrySet()) {
			if(entry.getKey().equals(deviceID)) {
				return entry.getValue();
			}
		}
		return null;
		
	}
}
