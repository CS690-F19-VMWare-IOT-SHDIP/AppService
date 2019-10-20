package cs.usfca.edu.edgex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cs.usfca.edu.edgex.device.BulbDevice;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.event.Event;
import cs.usfca.edu.edgex.event.adhocevent.RandomModEvent;
import cs.usfca.edu.edgex.event.deviceevent.DeviceEvent;
import cs.usfca.edu.edgex.event.deviceevent.LightOnEvent;
import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.utils.Flow;
import cs.usfca.edu.edgex.utils.Node;

/**
 * Driver class for AppService.
 *
 */
@SpringBootApplication
public class AppService {
	public static void main(String args[]) throws InterruptedException {
		SpringApplication.run(AppService.class, args);
//		System.out.println("Welcome to IOT workflow!");
//		DeviceModel bulbDeviceModel = new DeviceModel("dummy", "dummy", "dummy");
//		Device<Boolean> bulb = new BulbDevice(bulbDeviceModel);
//		DeviceEvent<Boolean> lightOnEvent = new LightOnEvent(bulb);
//		Event randomModEvent = new RandomModEvent(7);
//		Node child = new Node();
//		child.addEvent(lightOnEvent);
//		Node head = new Node();
//		head.addEvent(randomModEvent);
//		head.addChildren(child);
//		Flow flow = new Flow(head);
//		flow.executeFlow();
	}
}
