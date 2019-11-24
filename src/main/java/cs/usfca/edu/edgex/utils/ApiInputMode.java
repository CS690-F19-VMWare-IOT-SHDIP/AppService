package cs.usfca.edu.edgex.utils;

/**
 * Defines the input mode of for the files.
 * 
 * IndividualAPI mode is the mode where the device, events 
 * and flows are created via separate API calls.
 * 
 * AggregateAPI mode is the one where the devices, events and flows
 * are loaded into one JSON file and the system reads the file to
 * create them all in just one API call.
 *
 */
public enum ApiInputMode {
	IndividualAPI,
	AggregateAPI
}
