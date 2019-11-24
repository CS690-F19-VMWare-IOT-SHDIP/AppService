package cs.usfca.edu.edgex.apis.flowapis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import cs.usfca.edu.edgex.aggregateapis.InputModelHandlers;
import cs.usfca.edu.edgex.apis.eventapis.EventHandlers;
import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.event.Event;
import cs.usfca.edu.edgex.exceptions.EventNotFoundException;
import cs.usfca.edu.edgex.exceptions.FlowNotFoundException;
import cs.usfca.edu.edgex.model.FlowModel;
import cs.usfca.edu.edgex.model.NodeModel;
import cs.usfca.edu.edgex.utils.ApiInputMode;
import cs.usfca.edu.edgex.utils.Flow;
import cs.usfca.edu.edgex.utils.Node;

public class FlowHandlers {
	
	/**
	 *  activeFlows stores the flow's status
	 *  when a flow is created, it will be inactive by default and set to false
	 *  only activate a flow when activateFlow API is called
	 */
	private static ConcurrentHashMap<String, Flow> allFlows = new ConcurrentHashMap<String, Flow>();
	private static FlowModel flowMod = null;
	
	/**
	 * Adds a flow to the flow database.
	 * This function works with the /addFlow api to receive a flow from the user
	 * in JSON format and add to the database.
	 * @param flowModel	- json object of the user's flow input
	 * @return flowId
	 * @throws EventNotFoundException
	 */
	public static String addFlow(FlowModel flowModel, ApiInputMode inputMode) 
			throws EventNotFoundException, FlowNotFoundException {
		Node head = new Node(null);
		flowMod = flowModel;
		head = addEventsToNode(flowModel.getHead(), head, inputMode);
		head.setNodeId(flowModel.getHead().nodeId);
		for(String childNodeId: flowModel.getHead().childNodeIds) {
			addChildren(head, childNodeId, inputMode);
		}
		String flowId = UUID.randomUUID().toString();
		while(allFlows.containsKey(flowId)) {
			flowId = UUID.randomUUID().toString();
		}
		Flow flow = new Flow(head, flowId);
		allFlows.put(flowId, flow);
		if(inputMode == ApiInputMode.AggregateAPI) {
			activateFlow(flow.getFlowId());
		}
		flowMod = null;
		return flowId;
	}
	
	/**
	 * Adds all the events from the to the newly created corresponding node in memory
	 * @param nodeModel
	 * @param node
	 * @param inputMode
	 * @return
	 * @throws EventNotFoundException
	 */
	private static Node addEventsToNode(NodeModel nodeModel, Node node, ApiInputMode inputMode) throws EventNotFoundException{
		if(inputMode == ApiInputMode.IndividualAPI) {
			for(String eventId: nodeModel.eventIds) {
				if(!EventHandlers.getEvents().containsKey(eventId))
					throw new EventNotFoundException(eventId);
				node.addEvent(EventHandlers.getEvents().get(eventId));
			}
		}
		else if(inputMode == ApiInputMode.AggregateAPI) {
			for(String eventIndex: nodeModel.eventIds) {
				String eventId = InputModelHandlers.getEventIDs().get(Integer.parseInt(eventIndex));
				if(!EventHandlers.getEvents().containsKey(eventId))
					throw new EventNotFoundException(eventId);
				node.addEvent(EventHandlers.getEvents().get(eventId));
			}
		}
		return node;
	}
	
	/**
	 * Recursively adds children to nodes, starting from children of the head nodes
	 * Reads from the input JSON to determine what children to add
	 * @param node
	 * @param childNodeId
	 * @param inputMode
	 * @return
	 * @throws EventNotFoundException
	 */
	private static Node addChildren(Node node, String childNodeId, ApiInputMode inputMode) throws EventNotFoundException {
		NodeModel nm = findChildFromInput(flowMod, childNodeId);
		if(nm == null) {
			System.out.println("addChildren() terminating at node: " + childNodeId + ". No further child nodes exist" );
			return node;
		}
		Node child = new Node(nm.nodeId);
		if(nm.childNodeIds.size() == 0) {
			child = addEventsToNode(nm, child, inputMode);
			node.addChildren(child);
			return node;
		}
		child = addEventsToNode(nm, child, inputMode);	
		for(String childNode: nm.childNodeIds) {
			addChildren(child, childNode, inputMode);
		}
		node.addChildren(child);
		return node;
	}
	
	private static NodeModel findChildFromInput(FlowModel flowModel, String nodeId) {
		for(NodeModel nm: flowModel.getNodeList()) {
			if(nm.nodeId.equals(nodeId))
				return nm;
		}
		return null;
	}
	
	/**
	 * Marks a flow as active so that the flow can be processed.
	 * Only active flows are processed.
	 * @param flowId
	 * @return boolean
	 * @throws FlowNotFoundException 
	 */
	public static void activateFlow(String flowId) throws FlowNotFoundException {
		if(allFlows.get(flowId) == null)
			throw new FlowNotFoundException(flowId);
		allFlows.get(flowId).activate();
	}
	
	/**
	 * Marks a flow as inactive so that the flow can stop being processed.
	 * Inactive flows are still stored in database but just no longer processed.
	 * @param flowId
	 * @return boolean
	 * @throws FlowNotFoundException 
	 */
	public static void deactivateFlow(String flowId) throws FlowNotFoundException {
		if(allFlows.get(flowId) == null)
			throw new FlowNotFoundException(flowId);
		allFlows.get(flowId).deActivate();
	}
	
	/**
	 * Removes a flow from the database.
	 * @param flowId
	 * @return String - success or error message
	 * @throws FlowNotFoundException 
	 */
	public static void deleteFlow(String flowId) throws FlowNotFoundException {
		if(allFlows.remove(flowId) == null) {
			throw new FlowNotFoundException(flowId);
		}
	}
	
	/**
	 * Returns all the flows that exist in database.
	 * @return HashMap - <flowId, Flow>
	 */
	public static ConcurrentHashMap<String, Flow> getAllFlows() {
		return allFlows;
	}
	
	/**
	 * Returns all the active flows that exist in database.
	 * @return List - <Flow>
	 */
	public static ConcurrentLinkedQueue<Flow> getActiveFlows() {
		ConcurrentLinkedQueue<Flow> result = new ConcurrentLinkedQueue<Flow>();
		for(Entry<String, Flow> entry: allFlows.entrySet()) {
			if(entry.getValue().isActive()) {
				result.add(allFlows.get(entry.getKey()));
			}
		}
		return result;
	}
	
	/**
	 * Returns the ids of all the active flows that exist in database.
	 * @return List<String> - list of active flowIds
	 */
	public static List<String> listAllActiveFlowIds() {
		List<String> result = new ArrayList<String>();
		for(Flow flow: getActiveFlows()) {
			result.add(flow.getFlowId());
		}
		return result;
	}
	
	/**
	 * Returns the ids of all the flows that exist in database.
	 * @return List<String> - list of all flowIds
	 */
	public static List<String> listAllFlowIds() {
		List<String> result = new ArrayList<String>();
		for(String key: allFlows.keySet()) {
			result.add(key);
		}
		return result;
	}
	
	/**
	 * Check if the Device is occupied by any node.
	 * @param device
	 * @return boolean
	 */
	public static boolean isDeviceOccupied(Device<?> device) {
		for(Entry<String, Flow> entry: allFlows.entrySet()) {
			if(checkInNode(entry.getValue().getHead(), device)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Helper method to check if any node in flow 
	 * contains provided device.
	 * @param node
	 * @param device
	 * @return boolean
	 */
	private static boolean checkInNode(Node node, Device<?> device) {
		if(node == null) {
			return false;
		}
		for(Event event: node.getEvents()) {
			if(event.getDevice().equals(device)) {
				return true;
			}
		}
		for(Node child: node.getChildren()) {
			if(checkInNode(child, device)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Helper method to check if any node in flow 
	 * contains provided device.
	 * @param node
	 * @param device
	 * @return boolean
	 */
	private static boolean checkInNode(Node node, Event event) {
		if(node == null) {
			return false;
		}
		if(node.getEvents().contains(event)) {
			return true;
		}
		for(Node child: node.getChildren()) {
			if(checkInNode(child, event)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if the Event is occupied by any node.
	 * @param event
	 * @return boolean
	 */
	public static boolean isEventOccupied(Event event) {
		for(Entry<String, Flow> entry: allFlows.entrySet()) {
			if(checkInNode(entry.getValue().getHead(), event)) {
				return true;
			}
		}
		return false;
	}
}
