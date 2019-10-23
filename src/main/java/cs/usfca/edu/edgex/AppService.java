package cs.usfca.edu.edgex;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cs.usfca.edu.edgex.apis.flowapis.FlowHandlers;
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
	public static void main(String args[]) throws InterruptedException, UnsupportedDeviceTypeException, ExecutionException {
		SpringApplication.run(AppService.class, args);
		monitorFlows();
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
	
	public static void monitorFlows() throws InterruptedException, ExecutionException {
		while(true) {
			System.out.println("Starting a new round of monitoring flow heads...");
			ExecutorService executorService  = Executors.newCachedThreadPool();
			List<Future<Boolean>> futures = new ArrayList();
			for(Flow flow: FlowHandlers.getActiveFlows()) {
				System.out.println("Processing active flows...");
				Future<Boolean> future = executorService.submit(() -> flow.executeFlow());
				futures.add(future);
			}
			for(Future f: futures) {
				System.out.println("Future returned: " + f.get());
			}
			Thread.sleep(5000);
		}
	}
}
