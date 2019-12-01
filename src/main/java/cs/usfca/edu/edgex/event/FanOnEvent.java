package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;

/**
 * FantOnEvent is used to trigger ON on given device.
 *
 */
public class FanOnEvent implements Event {
	
	private static DeviceType deviceType = DeviceType.FAN;
	private Device<Boolean> fan;
	
	/**
	 * Constructor for event with LED device.
	 * @param led
	 * @throws UnsupportedDeviceTypeException 
	 */
	public FanOnEvent(Device<Boolean> fan) throws UnsupportedDeviceTypeException {
		if(fan.getDeviceType() != deviceType) {
			throw new UnsupportedDeviceTypeException(fan.getDeviceType(), deviceType);
		}
		this.fan = fan;
	}

	/**
	 * Checks if the current fan device is switched
	 * ON or not. It returns true if fan is ON and
	 * returns false otherwise.
	 * @param boolean
	 */
	@Override
	public boolean isActive() {
		return fan.get();
	}

	/**
	 * Triggers an event to switch ON the fan.
	 */
	@Override
	public void trigger() {
		System.out.println("%%% Turning on the fan");
		if(fan.get() == null || !fan.get()) {
			fan.set(true);
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
		return this.fan;
	}

	@Override
	public boolean equals(Event event) {
		return (this.getClass().getSimpleName().equals(event.getClass().getSimpleName())
				&& this.fan.equals(event.getDevice()));
	}

}
