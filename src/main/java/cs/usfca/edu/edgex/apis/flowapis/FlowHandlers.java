package cs.usfca.edu.edgex.apis.flowapis;

import java.util.HashMap;
import java.util.Map;

import cs.usfca.edu.edgex.exceptions.EventNotFoundException;
import cs.usfca.edu.edgex.model.FlowModel;
import cs.usfca.edu.edgex.model.NodeModel;
import cs.usfca.edu.edgex.utils.Flow;
import cs.usfca.edu.edgex.utils.Node;

public class FlowHandlers {
	
	private static Map<String, Flow> allFlows = new HashMap<String, Flow>();
	private static Map<String, Boolean> activeFlows = new HashMap<String, Boolean>();
	
	public static String addFlow(FlowModel flowModel) {
		Node head = new Node();
		//for(String event: )
		//Flow flow = new Flow();
		return null;
	}
	
	private Node addEvents(NodeModel nodeModel) throws EventNotFoundException{
		for(String event: nodeModel.eventIds) {
			
		}
		return null;
	}
}
