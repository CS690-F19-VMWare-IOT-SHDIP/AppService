package cs.usfca.edu.edgex.edgexclient;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class DeviceEvent {
	
	@SerializedName("device")
	private String deviceName;
	private ArrayList<Readings> readings;
	
	public DeviceEvent(String deviceName, ArrayList<Readings> readings) {
		this.deviceName = deviceName;
		this.readings = readings;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public ArrayList<Readings> getReadings() {
		return readings;
	}

}
