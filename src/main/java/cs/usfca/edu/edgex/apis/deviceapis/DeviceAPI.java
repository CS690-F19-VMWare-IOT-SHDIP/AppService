package cs.usfca.edu.edgex.apis.deviceapis;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.model.DeviceIdModel;
import cs.usfca.edu.edgex.model.DeviceModel;
import cs.usfca.edu.edgex.model.ErrorModel;

@Controller
@RequestMapping("/device")
public class DeviceAPI {
	
	// TODO: Add "Remove RandomModEvent" after flow/node APIs are complete.
	
	/**
	 * Returns the list of all devices.
	 * @return Map<String, DeviceModel>
	 */
	@GetMapping(value = "/list")
	@ResponseBody()
	public Map<String, DeviceModel> listAllDevices() {
		return DeviceHandlers.getAllPhysicalDevices();
	}
	
	/**
	 * Registers new physical device.
	 * @param deviceModel
	 * @return ResponseEntity<?>
	 */
	@PostMapping(value = "/register", consumes = "application/json")
	@ResponseBody()
	public ResponseEntity<?> registerDevice(@RequestBody DeviceModel deviceModel) {
		String resp = DeviceHandlers.registerPhysicalDevice(deviceModel);
		if(resp != null) {
			return ResponseEntity.status(HttpStatus.OK).body(new DeviceIdModel(resp));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorModel("Request contains unsupported device type: " + deviceModel.getDeviceType()));
	}
	
	/**
	 * Get device with provided deviceId.
	 * @param deviceId
	 * @return ResponseEntity<?>
	 */
	@GetMapping(value = "/{deviceId}")
	@ResponseBody()
	public ResponseEntity<?> getPhysicalDevice(@PathVariable(value = "deviceId") String deviceId) {
		DeviceModel device = DeviceHandlers.getPhysicalDeviceForDeviceId(deviceId);
		if(device != null) {
			return ResponseEntity.status(HttpStatus.OK).body(device);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorModel("No Device with DeviceId: " + deviceId + " Found!"));
	}
	
	/**
	 * Returns list of devices with provided deviceType.
	 * @param deviceType
	 * @return Map<String, DeviceModel>
	 */
	@GetMapping(value = "/list/type/{deviceType}")
	@ResponseBody()
	public Map<String, DeviceModel> listDeviceForType(@PathVariable(value = "deviceType") DeviceType deviceType) {
		return DeviceHandlers.listPhysicalDeviceWithType(deviceType);
	}
	
	/**
	 * Returns list of device types.
	 * @return List<DeviceType>
	 */
	@GetMapping(value = "/list/type")
	@ResponseBody()
	public List<DeviceType> listDeviceTypes() {
		return DeviceHandlers.listDeviceTypes();
	}
	
}
