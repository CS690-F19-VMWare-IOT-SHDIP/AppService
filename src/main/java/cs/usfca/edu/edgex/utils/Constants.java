package cs.usfca.edu.edgex.utils;

import java.util.HashSet;

public class Constants {
	
	public static HashSet<Integer> SUPPORTED_PINS = new HashSet<Integer>();
	
	public Constants() {
		SUPPORTED_PINS.add(13);
		SUPPORTED_PINS.add(19);
		SUPPORTED_PINS.add(26);
	}
}
