package cs.usfca.edu.edgex.event;

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
}
