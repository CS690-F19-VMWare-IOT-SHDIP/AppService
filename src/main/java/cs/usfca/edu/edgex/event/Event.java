package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;

/**
 * Interface to support different type of Events.
 */
public interface Event extends Comparable<Event> {
	
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
	
	public Device<?> getDevice();
}
