package cs.usfca.edu.edgex.apis.deviceapis;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cs.usfca.edu.edgex.apis.eventapis.EventHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.device.VirtualRandomModDevice;
import cs.usfca.edu.edgex.model.DeviceIdModel;
import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.model.ErrorModel;

@Controller
@RequestMapping("/devices")
public class DeviceAPI {
	
	@GetMapping(value = "/listAllPhysicalDevices")
	@ResponseBody()
	public Map<String, DeviceModel> listAllDevices() {
		return DeviceHandlers.getAllPhysicalDevices();
	}
	
	@PostMapping(value = "/registerDevice", consumes = "application/json")
	@ResponseBody()
	public ResponseEntity<?> registerDevice(@RequestBody DeviceModel deviceModel) {
		// TODO: Compare current device object with already present in map before adding.
		String resp = DeviceHandlers.registerPhysicalDevice(deviceModel);
		if(resp != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new DeviceIdModel(resp));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorModel("Request contains unsupported device type: " + deviceModel.getDeviceType()));
	}
	
	@GetMapping(value = "/getPhysicalDevice/{deviceId}")
	@ResponseBody()
	public ResponseEntity<?> getPhysicalDevice(@PathVariable(value = "deviceId") String deviceId) {
		DeviceModel device = DeviceHandlers.getPhysicalDeviceForDeviceId(deviceId);
		if(device != null) {
			return ResponseEntity.status(HttpStatus.OK).body(device);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorModel("No Device with DeviceId: " + deviceId + " Found!"));
	}
	
	@GetMapping(value = "/listPhysicalDeviceWithType/{deviceType}")
	@ResponseBody()
	public Map<String, DeviceModel> listDeviceForType(@PathVariable(value = "deviceType") DeviceType deviceType) {
		return DeviceHandlers.listPhysicalDeviceWithType(deviceType);
	}
	
	@GetMapping(value = "/listDeviceTypes")
	@ResponseBody()
	public List<DeviceType> listDeviceTypes() {
		return DeviceHandlers.listDeviceTypes();
	}
	
}
