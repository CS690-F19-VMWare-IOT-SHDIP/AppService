package cs.usfca.edu.edgex.event;

import org.apache.tomcat.jni.Time;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;

public class LEDOnEvent implements Event {
	
	private static DeviceType deviceType = DeviceType.LED;
	private Device<Boolean> led;
	
	/**
	 * Constructor for event with LED device.
	 * @param led
	 * @throws UnsupportedDeviceTypeException 
	 */
	public LEDOnEvent(Device<Boolean> led) throws UnsupportedDeviceTypeException {
		if(led.getDeviceType() != deviceType) {
			throw new UnsupportedDeviceTypeException(led.getDeviceType(), deviceType);
		}
		this.led = led;
	}

	/**
	 * Checks if the current LED device is switched
	 * ON or not. It returns true if LED is ON and
	 * returns false otherwise.
	 * @param boolean
	 */
	public boolean isActive() {
		return led.get();
	}
	
	/**
	 * Triggers an event to switch ON the LED.
	 */
	public void trigger() {
		if(led.get() == null || !led.get()) {
			Thread t = new Thread() {
				public void run() {
					try {
						this.sleep(3000);
						led.set(false);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			try {
				Thread.sleep(1000);
				led.set(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.start();
			 // Triggers LED on.
		}
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
		return this.led;
	}
	
	@Override
	public boolean equals(Event event) {
		return (this.getClass().getSimpleName().equals(event.getClass().getSimpleName())
				&& this.led.equals(event.getDevice()));
	}

}
