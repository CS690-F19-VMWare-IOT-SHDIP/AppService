package cs.usfca.edu.edgex.model;

public class DeviceModel {
	private String deviceName;
	private String command;
	private String resourceName;
	
	public DeviceModel(String deviceName, String command, String resourceName) {
		this.deviceName = deviceName;
		this.command = command;
		this.resourceName = resourceName;
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
