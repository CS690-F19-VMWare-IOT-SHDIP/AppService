package cs.usfca.edu.edgex.device;

import cs.usfca.edu.edgex.event.deviceevent.DeviceEvent;

public interface Device<T> {
	public T get();
	public void set(T val);
	public boolean isSupported(DeviceEvent<T> event);
	public boolean addEvent(DeviceEvent<T> event);
}
