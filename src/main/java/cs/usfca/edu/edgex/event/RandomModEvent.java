package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;

/**
 * It generates a random number and calculates
 * mod with given value. If the remainder is 0
 * then the event is true.
 */
public class RandomModEvent implements Event {
	
	private static DeviceType deviceType = DeviceType.VIRTUAL;
	private Device<Integer> device;
	
	/**
	 * RandomModEvent constructor that takes n.
	 * @param n
	 * @throws UnsupportedDeviceTypeException 
	 */
	public RandomModEvent(Device<Integer> device) throws UnsupportedDeviceTypeException {
		if(device.getDeviceType() != deviceType) {
			throw new UnsupportedDeviceTypeException(device.getDeviceType(), DeviceType.VIRTUAL);
		}
		this.device = device;
	}
	
	/**
	 * Checks if the random mod event is true or not.
	 * @return boolean
	 */
	public boolean isActive() {
		boolean flag = (device.get() == 0);
		System.out.println("** RandomModEvent is: " + flag);
		return flag;
	}
	
	/**
	 * Trigger is disabled for this event.
	 */
	public void trigger() {
		System.out.println("RandomModEvent doesn't support trigger!");
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
		return this.device;
	}
	
	@Override
	public boolean equals(Event event) {
		return (this.getClass().getSimpleName().equals(event.getClass().getSimpleName()) 
				&& this.device.equals(event.getDevice()));
	}
	
}
