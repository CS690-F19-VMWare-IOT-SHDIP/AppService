package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;

/**
 * LightOnEvent is used to trigger light ON on given device.
 *
 */
public class LightOnEvent implements Event {
	
	private static DeviceType deviceType = DeviceType.BULB;
	private Device<Boolean> bulb;
	
	/**
	 * Constructor for event with bulb device.
	 * @param bulb
	 * @throws UnsupportedDeviceTypeException 
	 */
	public LightOnEvent(Device<Boolean> bulb) throws UnsupportedDeviceTypeException {
		if(bulb.getDeviceType() != DeviceType.BULB) {
			throw new UnsupportedDeviceTypeException(bulb.getDeviceType(), DeviceType.BULB);
		}
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
	 * Static method to identify DeviceType that
	 * this event supports.
	 * @return Devicetype
	 */
	public static DeviceType getDeviceType() {
		return deviceType;
	}

	@Override
	public Device<?> getDevice() {
		return this.bulb;
	}
	
	@Override
	public boolean equals(Event event) {
		return (this.getClass().getSimpleName().equals(event.getClass().getSimpleName()));
	}

}
