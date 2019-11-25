package cs.usfca.edu.edgex.aggregateapis;

import java.util.List;

import cs.usfca.edu.edgex.model.FlowModel;

/**
 * This class can take in devices, events and flow in a single file
 * And creates the devices, events and flow from a single API call
 * Eliminates the need for calling so many apis when testing a workflow
 *
 */
public class InputModel {
	/*
	 * String containing all devices to be configured each separated by "|"
	 * looks like: "{val" : "4", "deviceName" : "Virtual-01"} | {"val" : "4", "deviceName" : "Virtual-01"}"
	 */
	public String devices;
	/*
	 * DeviceType of every device
	 * Each device in the "devices" list above should have a corresponding deviceType here
	 * Device Type entered should be a prefix of the actual device class name in this code
	 * E.g bulb device type should have a name containing "Bulb", because bulb class is named "BulbDevice"
	 */
	public List<String> deviceType;
	/*
	 * Events to be configured for every device
	 * Event i should be configured for device i
	 */
	public List<String> events;
	
	public FlowModel flow;
}
