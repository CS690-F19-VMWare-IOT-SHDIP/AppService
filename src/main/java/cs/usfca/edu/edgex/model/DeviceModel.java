package cs.usfca.edu.edgex.model;

import cs.usfca.edu.edgex.device.DeviceType;

/**
 * DeviceModel specifies the device specific attributes
 * that can be used to call specific events on physical device.
 */

public abstract class DeviceModel {
	private String deviceID;
	private String deviceName;
	// TODO : Create a map to support single-device multiple-commands and resource-names.
	private String command;
	private String resourceName;
	private DeviceType deviceType;
	
	public DeviceType getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}
