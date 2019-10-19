package cs.usfca.edu.edgex.event.deviceevent;

import cs.usfca.edu.edgex.device.Device;

public class LightOnEvent implements DeviceEvent<Boolean> {
	
	private Device<Boolean> bulb;
	
	public LightOnEvent(Device<Boolean> bulb) {
		this.bulb = bulb;
	}
	
	public boolean isActive() {
		return bulb.get();
	}

	public void trigger() {
		if(!bulb.get()) {
			bulb.set(true); // Triggers bulb on.
		}
	}

	public Device<Boolean> getDevice() {
		return bulb;
	}

}
