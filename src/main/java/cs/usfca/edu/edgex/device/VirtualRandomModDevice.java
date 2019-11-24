package cs.usfca.edu.edgex.device;

import java.util.Random;

import cs.usfca.edu.edgex.model.Model;
import cs.usfca.edu.edgex.model.RandomModInput;
/**
 * Defines virtual random mod device
 */
public class VirtualRandomModDevice implements Device<Integer> {
	
	private Random random;
	private RandomModInput input;
	
	/**
	 * Constructor for creating VirtualRandomModDevice.
	 * @param input
	 */
	public VirtualRandomModDevice(Model input) {
		if(input instanceof RandomModInput) {
			this.input = (RandomModInput)input;
			this.random = new Random();
		}
		else {
			System.out.print("Wrong type passed in to VirtualRandomModDevice");
		}
	}
	
	/**
	 * Get random device value modulo with input.
	 * return Integer
	 */
	public Integer get() {
		return random.nextInt() % input.getVal();
	}

	/**
	 * Set is not supported by RandomModDevice.
	 */
	public void set(Integer val) {
		System.out.println("Unimplemented Method set on Device VirtualRandomModDevice");
	}
	
	/**
	 * Returns the current deviceType which is virtual.
	 * @return DeviceType
	 */
	public DeviceType getDeviceType() {
		return DeviceType.VIRTUAL;
	}

	/**
	 * Returns the device input model to compare with other devices.
	 * @return Object
	 */
	public Object getDevice() {
		return input;
	}
	
	/**
	 * Equals method to compare current device with other deviceTypes.
	 * @param device
	 * @return boolean
	 */
	@Override
	public boolean equals(Device<?> device) {
		if(device.getDevice() instanceof RandomModInput) {
			RandomModInput received = (RandomModInput) device.getDevice();
			if(received.getDeviceName().equals(input.getDeviceName()) && received.getVal() == input.getVal()) {
				return true;
			}
		}
		return false;
	}
	
}
