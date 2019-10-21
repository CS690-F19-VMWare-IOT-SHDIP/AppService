package cs.usfca.edu.edgex.device;

import java.util.Random;

import cs.usfca.edu.edgex.model.RandomModInput;

public class VirtualRandomModDevice implements Device<Integer> {

	private Random random;
	RandomModInput input;
	
	public VirtualRandomModDevice(RandomModInput input) {
		this.input = input;
		this.random = new Random();
	}
	
	public Integer get() {
		return random.nextInt() % input.getVal();
	}

	public void set(Integer val) {
		System.out.println("Unimplemented Method set on Device VirtualRandomModDevice");
	}

	public DeviceType getDeviceType() {
		return DeviceType.VIRTUAL;
	}

	public Object getDevice() {
		return input;
	}
	
}
