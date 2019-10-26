package cs.usfca.edu.edgex.edgexclient;

import com.google.gson.annotations.SerializedName;

public class Readings {
	
	@SerializedName("name")
	private String resourceName;
	private String value;

	public Readings(String resourceName, String value) {
		this.resourceName = resourceName;
		this.value = value;
	}
	
	public String getResourceName() {
		return resourceName;
	}
	
	public String getValue() {
		return value;
	}

}
