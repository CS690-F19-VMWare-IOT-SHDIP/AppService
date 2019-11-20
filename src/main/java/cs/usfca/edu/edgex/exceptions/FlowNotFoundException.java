package cs.usfca.edu.edgex.exceptions;

public class FlowNotFoundException extends Exception {
	public FlowNotFoundException(String flowId) {
		super("Flow with Id: " + flowId + " is not Present.");
	}
}
