package cs.usfca.edu.edgex.event.deviceevent;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.event.Event;

/**
 * Device event is an interface for events
 * that are executed on device. In addition
 * to methods of Event it also implements 
 * getDevice method.
 *
 * @param <T>
 */
public interface DeviceEvent<T> extends Event {
	/**
	 * It returns the device to which current 
	 * event is bind.
	 * @return Device<T>
	 */
	public Device<T> getDevice();
}
