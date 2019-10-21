package cs.usfca.edu.edgex.exceptions;

import cs.usfca.edu.edgex.device.DeviceType;

public class UnsupportedDeviceTypeException extends Exception {
	
	public UnsupportedDeviceTypeException(DeviceType actual, DeviceType expected) {
		super("Required DeviceType " + expected + " got " + actual);
	}

}
