package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;

/**
 * FantOffEvent is used to trigger OFF on given device.
 *
 */
public class FanOffEvent implements Event {
	private static DeviceType deviceType = DeviceType.FAN;
	private Device<Boolean> fan;
	
	/**
	 * Constructor for event with led device.
	 * @param led
	 * @throws UnsupportedDeviceTypeException 
	 */
	public FanOffEvent(Device<Boolean> fan) throws UnsupportedDeviceTypeException {
		if(fan.getDeviceType() != deviceType) {
			throw new UnsupportedDeviceTypeException(fan.getDeviceType(), deviceType);
		}
		this.fan = fan;
	}
	
	/**
	 * Checks if the current fan device is switched
	 * OFF or not. It returns true if led is OFF and
	 * returns false otherwise.
	 * @param boolean
	 */
	@Override
	public boolean isActive() {
		return !fan.get();
	}

	/**
	 * Triggers an event to switch OFF the fan.
	 */
	@Override
	public void trigger() {
		fan.set(false);
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
		return this.fan;
	}

	@Override
	public boolean equals(Event event) {
		return (this.getClass().getSimpleName().equals(event.getClass().getSimpleName())
				&& this.fan.equals(event.getDevice()));
	}

}
