package cs.usfca.edu.edgex.device;

import java.util.LinkedList;
import java.util.List;

import cs.usfca.edu.edgex.event.deviceevent.DeviceEvent;
import cs.usfca.edu.edgex.model.DeviceModel;

public class BulbDevice implements Device<Boolean> {
	
	private DeviceModel bulb;
	private List<DeviceEvent<Boolean>> events;
	private Boolean value;
	
	
	public BulbDevice(DeviceModel bulb) {
		this.bulb = bulb;
		this.events = new LinkedList<DeviceEvent<Boolean>>();
		this.value = false;
	}

	public Boolean get() {
		System.out.println("Bulb is: " + value);
		return value;
	}

	public void set(Boolean value) {
		System.out.println("Setting Bulb to: " + value);
		this.value = value;
	}

	public boolean isSupported(DeviceEvent<Boolean> event) {
		return events.contains(event);
	}

	public boolean addEvent(DeviceEvent<Boolean> event) {
		if(event.getDevice() == this) {
			events.add(event);
			return true;
		}
		return false;
	}

}
