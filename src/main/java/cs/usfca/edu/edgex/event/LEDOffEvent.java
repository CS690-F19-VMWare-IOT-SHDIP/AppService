package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;

/**
 * LightOffEvent is used to trigger light OFF on given device.
 *
 */
public class LEDOffEvent implements Event {
	
	private static DeviceType deviceType = DeviceType.LED;
	private Device<Boolean> led;
	
	/**
	 * Constructor for event with led device.
	 * @param led
	 * @throws UnsupportedDeviceTypeException 
	 */
	public LEDOffEvent(Device<Boolean> led) throws UnsupportedDeviceTypeException {
		if(led.getDeviceType() != deviceType) {
			throw new UnsupportedDeviceTypeException(led.getDeviceType(), deviceType);
		}
		this.led = led;
	}
	
	/**
	 * Checks if the current led device is switched
	 * OFF or not. It returns true if led is OFF and
	 * returns false otherwise.
	 * @param boolean
	 */
	public boolean isActive() {
		return !led.get();
	}
	
	/**
	 * Triggers an event to switch OFF the led.
	 */
	public void trigger() {
		led.set(false); // Triggers led off.
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
		return this.led;
	}

	@Override
	public boolean equals(Event event) {
		return (this.getClass().getSimpleName().equals(event.getClass().getSimpleName()));
	}
}
