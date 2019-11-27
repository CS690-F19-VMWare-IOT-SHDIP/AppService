package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;

public class IsBright implements Event {
	private static DeviceType deviceType = DeviceType.LDR;
	private Device<Integer> ldr;
	
	public IsBright(Device<Integer> ldr) throws UnsupportedDeviceTypeException {
		if(ldr.getDeviceType() != deviceType) {
			throw new UnsupportedDeviceTypeException(ldr.getDeviceType(), deviceType);
		}
		this.ldr = ldr;
	}
	
	public boolean isActive() {
		return ldr.get() == 1;
	}
	
	
	public void trigger() {
		throw new UnsupportedOperationException("Trigger not supported for IsDark");
	}
	
	
	public static DeviceType getDeviceType() {
		return deviceType;
	}

	@Override
	public Device<?> getDevice() {
		return this.ldr;
	}

	@Override
	public boolean equals(Event event) {
		return (this.getClass().getSimpleName().equals(event.getClass().getSimpleName())
				&& this.ldr.equals(event.getDevice()));
	}
}
