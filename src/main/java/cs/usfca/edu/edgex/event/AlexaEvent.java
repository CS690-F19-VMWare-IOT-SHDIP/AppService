package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.AlexaDevice;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;
import cs.usfca.edu.edgex.model.AlexaModel;

public class AlexaEvent implements Event {
	
	private static DeviceType deviceType = DeviceType.VIRTUAL;
	private Device<AlexaModel> device;
	
	public AlexaEvent(Device<AlexaModel> device) throws UnsupportedDeviceTypeException {
		if(device.getDeviceType() != deviceType) {
			throw new UnsupportedDeviceTypeException(device.getDeviceType(), DeviceType.VIRTUAL);
		}
		else {
			this.device = device;
		}
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		if(this.device instanceof AlexaDevice) {
			AlexaDevice alexaDevice = (AlexaDevice) this.device;
			if(alexaDevice.get() == null) {
				return false;
			}
			System.out.println("@@@ Alexa device got new intent input from Alexa skill");
			return true;
		}
		return false;
	}

	@Override
	public void trigger() {
		// TODO Auto-generated method stub
		System.out.println("Unimplemented method trigger for Alexa Device");
	}
	
	/**
	 * Static method to identify DeviceType that
	 * this event supports.
	 * @return Devicetype
	 */
	public static DeviceType getDeviceType() {
		return deviceType;
	}

	@Override
	public Device<?> getDevice() {
		return this.device;
	}

	@Override
	public boolean equals(Event event) {
		return (this.getClass().getSimpleName().equals(event.getClass().getSimpleName()) 
				&& this.device.equals(event.getDevice()));
	}

}
