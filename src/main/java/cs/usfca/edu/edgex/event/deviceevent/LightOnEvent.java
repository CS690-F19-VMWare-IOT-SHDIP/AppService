package cs.usfca.edu.edgex.event.deviceevent;

import cs.usfca.edu.edgex.device.Device;

/**
 * LightOnEvent is used to trigger light ON on given device.
 *
 */
public class LightOnEvent implements DeviceEvent<Boolean> {
	
	private Device<Boolean> bulb;
	
	/**
	 * Constructor for event with bulb device.
	 * @param bulb
	 */
	public LightOnEvent(Device<Boolean> bulb) {
		this.bulb = bulb;
	}
	
	/**
	 * Checks if the current bulb device is switched
	 * ON or not. It returns true if bulb is ON and
	 * returns false otherwise.
	 * @param boolean
	 */
	public boolean isActive() {
		return bulb.get();
	}
	
	/**
	 * Triggers an event to switch ON the bulb.
	 */
	public void trigger() {
		if(!bulb.get()) {
			bulb.set(true); // Triggers bulb on.
		}
	}
	
	/**
	 * returns the object to which current event is bind to.
	 */
	public Device<Boolean> getDevice() {
		return bulb;
	}

}
