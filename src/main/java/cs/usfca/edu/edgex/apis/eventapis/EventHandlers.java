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
	private static HashMap<String, Event> events = new HashMap<String, Event>();
	private static HashMap<String, ArrayList<Device<?>>> eventNameToDevices = new HashMap<String, ArrayList<Device<?>>>();
	
	/**
	 * Show all supported events from packages : cs.usfca.edu.edgex.event of class type Event.
	 * @return Set<String>
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
	
	
	/**
	 * List events pertaining to a specific device-type.
	 * @param type
	 * @return Set<String>
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
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
	
	/**
	 * Given an event name and device, check if concerned event class can be binded with the given device.
	 * If success, check if such an eventID already present or not and then return an eventID
	 * @param eventName
	 * @param device
	 * @return String
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
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
	
	/**
	 * Return all event names that are binded to a specific device.
	 * @param device
	 * @return ArrayList<String>
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
	
	/**
	 * Read through events map to check if eventID is created or not for an Event
	 * @param eventToDevice
	 * @return String
	 */
	public static String checkBindMap(Event eventToDevice) {
		for(Map.Entry<String, Event> entry : events.entrySet()) {
			
			if((entry.getValue().equals(eventToDevice)) && (entry.getValue().getDevice().equals(eventToDevice.getDevice()) == true)) { 
				return entry.getKey();
			}
		}
		return "";
	}
	
	/**
	 * Add new event binded to a device into map and return it's eventID
	 * @param eventToDevice
	 * @return String
	 */
	public static String addToBindMap(Event eventToDevice) {
		String eventID = UUID.randomUUID().toString();
		events.put(eventID, eventToDevice);
		return eventID;
	}
	
	/**
	 * Update map of eventName --> List of devices
	 * @param eventName
	 * @param device
	 */
	public static void updateEventNameToDevices(String eventName, Device<?> device) {
		if(eventNameToDevices.containsKey(eventName)) {
			System.out.println("APPEDNING : " + device);
			if(!eventNameToDevices.get(eventName).contains(device)) {
				eventNameToDevices.get(eventName).add(device);
			}
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
	
	/**
	 * Remove a specific event binded to a device using eventID. 
	 * Check if eventID is present in an active flow or not, if present, don't removie it.
	 * @param eventID
	 * @return boolean
	 */
	public static boolean removeEventID(String eventID) {
		if(events.containsKey(eventID)) {
			Device<?> deletedDevice = events.remove(eventID).getDevice();
//			removeDeviceFromAllEventNames(deletedDevice);
			return true;
		}
		return false;
	}
	
	/**
	 * Remove a device from map of events connected to a list of devices.
	 * @param device
	 */
	public static void removeDeviceFromAllEventNames(Device<?> device) {
		for(Map.Entry<String, ArrayList<Device<?>>> entry : eventNameToDevices.entrySet()) {
			if(entry.getValue().contains(device)) {
				String eventName = entry.getKey();
				ArrayList<Device<?>> devices = entry.getValue();
				devices.remove(device);
				eventNameToDevices.put(eventName, devices);
			}
		}
	}
	
	/**
	 * Remove a specific device from event to device
	 * @param eventName
	 * @param device
	 * @return
	 */
	public static boolean removeDeviceFromEventName(String eventName, Device<?> device) {
		if(eventNameToDevices.containsKey(eventName)) {
			ArrayList<Device<?>> deviceList = eventNameToDevices.get(eventName);
			deviceList.remove(device);
			eventNameToDevices.put(eventName, deviceList);
			return true;
		}
		return false;
	}
	
	/**
	 * Given a deviceID return a device. Check in physicalDevice and virtualDevice lists.
	 * @param deviceID
	 * @return Device<?>
	 */
	public static Device<?> getDeviceFromMap(String deviceID) {
		HashMap<String, PhysicalDevice<?>> physicalDeviceMap = (HashMap<String, PhysicalDevice<?>>) DeviceHandlers.getPhysicalDevices();
		HashMap<String, Device<?>> virtualDeviceMap = (HashMap<String, Device<?>>) VirtualDeviceHandlers.getVirtualDevices();
		Device<?> device = null;
		if(physicalDeviceMap.get(deviceID) != null) {
			device = physicalDeviceMap.get(deviceID);
		} else if(virtualDeviceMap.get(deviceID) != null) {
			device = virtualDeviceMap.get(deviceID);
		}
		return device;
	}
	
	/**
	 * Return map of eventID --> Event 
	 * @return HashMap<String, Event>
	 */
	public static Map<String, Event> getEvents() {
		return new HashMap<String, Event>(events);
	}
	
	/**
	 * Return map of eventName associated with it's list of devices.
	 * @return HashMap<String, ArrayList<Device<?>>>
	 */
	public static Map<String, ArrayList<Device<?>>> getEventNameToDevices() {
		return new HashMap<String, ArrayList<Device<?>>>(eventNameToDevices);
	}
}
