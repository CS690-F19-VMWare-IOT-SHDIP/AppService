package cs.usfca.edu.edgex.event.deviceevent;

import cs.usfca.edu.edgex.device.Device;

public class LightOffEvent implements DeviceEvent<Boolean> {
	
	private Device<Boolean> bulb;
	
	public LightOffEvent(Device<Boolean> bulb) {
		this.bulb = bulb;
	}

	public boolean isActive() {
		return !bulb.get();
	}

	public void trigger() {
		bulb.set(false); // Triggers bulb off.
	}

	public Device<Boolean> getDevice() {
		return bulb;
	}

}
