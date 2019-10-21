package cs.usfca.edu.edgex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.VirtualRandomModDevice;
import cs.usfca.edu.edgex.device.physicaldevices.BulbDevice;
import cs.usfca.edu.edgex.event.Event;
import cs.usfca.edu.edgex.event.LightOnEvent;
import cs.usfca.edu.edgex.event.RandomModEvent;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;
import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.utils.Flow;
import cs.usfca.edu.edgex.utils.Node;

/**
 * Driver class for AppService.
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
public class AppService {
	public static void main(String args[]) throws InterruptedException, UnsupportedDeviceTypeException {
		SpringApplication.run(AppService.class, args);
//		System.out.println("Welcome to IOT workflow!");
//		DeviceModel bulbDeviceModel = new DeviceModel("dummy", "dummy", "dummy");
//		Device<Boolean> bulb = new BulbDevice(bulbDeviceModel);
//		Event lightOnEvent = new LightOnEvent(bulb);
//		Device<Integer> randomModDevice = new VirtualRandomModDevice(15);
//		Event randomModEvent = new RandomModEvent(randomModDevice);
//		Node child = new Node();
//		child.addEvent(lightOnEvent);
//		Node head = new Node();
//		head.addEvent(randomModEvent);
//		head.addChildren(child);
//		Flow flow = new Flow(head);
//		flow.executeFlow();
	}
}
