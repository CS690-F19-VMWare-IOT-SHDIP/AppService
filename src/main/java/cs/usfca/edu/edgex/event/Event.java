package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;

/**
 * Interface to support different type of Events.
 */
public interface Event {
	
	/**
	 * Checks if the current event is active
	 * on a given device.
	 * @return boolean
	 */
	public boolean isActive();
	
	/**
	 * Triggers current event on given device.
	 */
	public void trigger();
	
	/**
	 * Returns the bind to current event.
	 * @return
	 */
	public Device<?> getDevice();
	
	/**
	 * Overriding equals to compare two devices.
	 * @return boolean
	 */
	public boolean equals(Event event);
}
