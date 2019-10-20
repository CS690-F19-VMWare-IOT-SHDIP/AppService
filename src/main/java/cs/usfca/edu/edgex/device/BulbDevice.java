package cs.usfca.edu.edgex.device;

import java.util.LinkedList;
import java.util.List;

import cs.usfca.edu.edgex.event.deviceevent.DeviceEvent;
import cs.usfca.edu.edgex.model.DeviceModel;

/**
 * Device for device type Bulb.
 */
public class BulbDevice implements Device<Boolean> {
	
	private DeviceModel bulb;
	private List<DeviceEvent<Boolean>> events;
	private Boolean value;
	
	
	public BulbDevice(DeviceModel bulb) {
		this.bulb = bulb;
		this.events = new LinkedList<DeviceEvent<Boolean>>();
		this.value = false;
	}

	/**
	 * Execute get api call on device-service.
	 * This will return the state of current Bulb device.
	 * @return Boolean
	 */
	public Boolean get() {
		System.out.println("Bulb is: " + value);
		return value;
	}
	
	/**
	 * Execute set api call on device-service.
	 * This will set the state of current Bulb device.
	 * @param value
	 */
	public void set(Boolean value) {
		System.out.println("Setting Bulb to: " + value);
		this.value = value;
	}
	
	/**
	 * Checks if the current event is supported by the Bulb
	 * device.
	 * @param event
	 * @return boolean
	 */
	public boolean isSupported(DeviceEvent<Boolean> event) {
		return events.contains(event);
	}

	public boolean addEvent(DeviceEvent<Boolean> event) {
		if(event.getDevice() == this) {
			events.add(event);
			return true;
		}
		return false;
	}

}
