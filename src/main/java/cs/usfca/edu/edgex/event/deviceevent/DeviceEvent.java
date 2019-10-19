package cs.usfca.edu.edgex.event.deviceevent;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.event.Event;

public interface DeviceEvent<T> extends Event {
	public Device<T> getDevice();
}
