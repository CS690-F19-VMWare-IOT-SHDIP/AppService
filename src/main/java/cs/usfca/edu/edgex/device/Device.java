package cs.usfca.edu.edgex.device;

/**
 * Interface to support different type of devices.
 *
 * @param <T>
 */
public interface Device<T> {
	/**
	 * Execute get api call on device-service.
	 * This will return the state of current device.
	 * @return T
	 */
	public T get();
	
	/**
	 * Execute set api call on device-service.
	 * This will set the state of current device.
	 * @param val
	 */
	public void set(T val);
	
	/**
	 * Checks if the current event is supported by the device.
	 * @param event
	 * @return boolean
	 */
	public DeviceType getDeviceType();
}
