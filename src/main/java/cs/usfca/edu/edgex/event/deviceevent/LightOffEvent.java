package cs.usfca.edu.edgex.event.deviceevent;

import cs.usfca.edu.edgex.device.Device;

/**
 * LightOffEvent is used to trigger light OFF on given device.
 *
 */
public class LightOffEvent implements DeviceEvent<Boolean> {
	
	private Device<Boolean> bulb;
	
	/**
	 * Constructor for event with bulb device.
	 * @param bulb
	 */
	public LightOffEvent(Device<Boolean> bulb) {
		this.bulb = bulb;
	}
	
	/**
	 * Checks if the current bulb device is switched
	 * OFF or not. It returns true if bulb is OFF and
	 * returns false otherwise.
	 * @param boolean
	 */
	public boolean isActive() {
		return !bulb.get();
	}
	
	/**
	 * Triggers an event to switch OFF the bulb.
	 */
	public void trigger() {
		bulb.set(false); // Triggers bulb off.
	}
	
	/**
	 * returns the object to which current event is bind to.
	 */
	public Device<Boolean> getDevice() {
		return bulb;
	}

}
