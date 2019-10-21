package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;

/**
 * LightOffEvent is used to trigger light OFF on given device.
 *
 */
public class LightOffEvent implements Event {
	
	private static DeviceType deviceType = DeviceType.BULB;
	private Device<Boolean> bulb;
	
	/**
	 * Constructor for event with bulb device.
	 * @param bulb
	 * @throws UnsupportedDeviceTypeException 
	 */
	public LightOffEvent(Device<Boolean> bulb) throws UnsupportedDeviceTypeException {
		if(bulb.getDeviceType() != DeviceType.BULB) {
			throw new UnsupportedDeviceTypeException(bulb.getDeviceType(), DeviceType.BULB);
		}
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
	 * Static method to identify DeviceType that
	 * this event supports.
	 * @return Devicetype
	 */
	public static DeviceType getDeviceType() {
		return deviceType;
	}
}
