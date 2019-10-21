package cs.usfca.edu.edgex.device;

import java.util.Random;

public class VirtualRandomModDevice implements Device<Integer> {

	private Random random;
	private int n;
	
	public VirtualRandomModDevice(int n) {
		this.n = n;
		this.random = new Random();
	}
	
	public Integer get() {
		return random.nextInt() % n;
	}

	public void set(Integer val) {
		System.out.println("Unimplemented Method set on Device VirtualRandomModDevice");
	}

	public DeviceType getDeviceType() {
		return DeviceType.VIRTUAL;
	}
	
}
