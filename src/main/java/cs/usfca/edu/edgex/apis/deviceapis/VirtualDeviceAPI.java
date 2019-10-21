package cs.usfca.edu.edgex.apis.deviceapis;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.model.DeviceIdModel;
import cs.usfca.edu.edgex.model.RandomModInput;

@Controller
@RequestMapping("/virtualDevice")
public class VirtualDeviceAPI {
	
	// TODO: Add "Remove RandomModEvent" after flow/node APIs are complete.
	
	@GetMapping(value = "/listVirtualDeviceTypes")
	@ResponseBody()
	public Set<String> listVirtualDeviceTypes() {
		return VirtualDeviceHandlers.listVirtualDeviceTypes();
	}
	
	@PostMapping(value = "/addDevice/RandomModEvent", consumes = "application/json")
	@ResponseBody()
	public DeviceIdModel addRandomModEvent(@RequestBody RandomModInput input) {
		return new DeviceIdModel(VirtualDeviceHandlers.addRandomModDevice(input));
	}
	
	@GetMapping(value = "/listAllVirtualDevices")
	@ResponseBody()
	public Map<String, Device<?>> listAllVirtualDevices() {
		return VirtualDeviceHandlers.listAllVirtualDevices();
	}
	
	@GetMapping(value = "/listDeviceWithId/{deviceId}")
	@ResponseBody()
	public Device<?> listDeviceWithType(@PathVariable(value = "deviceId") String deviceId) {
		// TODO: check if return value is null
		return VirtualDeviceHandlers.listDeviceWithId(deviceId);
	}
}
