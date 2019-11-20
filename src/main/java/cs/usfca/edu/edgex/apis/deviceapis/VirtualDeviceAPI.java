package cs.usfca.edu.edgex.apis.deviceapis;

import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.model.DeviceIdModel;
import cs.usfca.edu.edgex.model.ErrorModel;
import cs.usfca.edu.edgex.model.RandomModInput;

@Controller
@RequestMapping("/virtual")
public class VirtualDeviceAPI {
	
	// TODO: Add "Remove RandomModDevice" after flow/node APIs are complete.
	// TODO: List all VirtualDevices with given VirtualDeviceType.
	
	/**
	 * List all virtual device types.
	 * @return Set<String>
	 */
	@GetMapping(value = "/list/type")
	@ResponseBody()
	public Set<String> listVirtualDeviceTypes() {
		return VirtualDeviceHandlers.listVirtualDeviceTypes();
	}
	
	/**
	 * Register virtualRandomModDevice.
	 * @param input
	 * @return DeviceIdModel
	 */
	@PostMapping(value = "/register/VirtualRandomModDevice", consumes = "application/json")
	@ResponseBody()
	public DeviceIdModel addRandomModEvent(@RequestBody RandomModInput input) {
		// TODO : spring bug check, incorrect object passed by user
		return new DeviceIdModel(VirtualDeviceHandlers.addRandomModDevice(input));
	}
	
	/**
	 * List all virtual devices.
	 * @return Map<String, Device<?>>
	 */
	@GetMapping(value = "/list")
	@ResponseBody()
	public Map<String, Device<?>> listAllVirtualDevices() {
		return VirtualDeviceHandlers.listAllVirtualDevices();
	}
	
	/**
	 * List virtual device with provided deviceId.
	 * @param deviceId
	 * @return ResponseEntity<?>
	 */
	@GetMapping(value = "/{deviceId}")
	@ResponseBody()
	public ResponseEntity<?> getDeviceWithId(@PathVariable(value = "deviceId") String deviceId) {
		Device<?> device = VirtualDeviceHandlers.getDeviceWithId(deviceId);
		if(device != null) {
			return ResponseEntity.status(HttpStatus.OK).body(device);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorModel("No Device with DeviceId: " + deviceId + " Found!"));
	}
	
	/**
	 * Remove device with given device id.
	 * @param String
	 */
	@DeleteMapping(value = "/{deviceId}")
	@ResponseBody()
	public ResponseEntity<?> removeVirtualDevice(@PathVariable(value = "deviceId") String deviceId) {
		if(VirtualDeviceHandlers.removeDevice(deviceId)) {
			return ResponseEntity.status(HttpStatus.OK).body("Deleted VirtualDevice with DeviceId: " + deviceId);
		}
		return ResponseEntity.status(HttpStatus.OK).body("Device with DeviceId: " + deviceId + " does not exist");
	}
}
