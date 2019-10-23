package cs.usfca.edu.edgex.apis.flowapis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import cs.usfca.edu.edgex.apis.eventapis.EventHandlers;
import cs.usfca.edu.edgex.exceptions.EventNotFoundException;
import cs.usfca.edu.edgex.model.FlowModel;
import cs.usfca.edu.edgex.model.NodeModel;
import cs.usfca.edu.edgex.utils.Constants;
import cs.usfca.edu.edgex.utils.Flow;
import cs.usfca.edu.edgex.utils.Node;

public class FlowHandlers {
	
	private static ConcurrentHashMap<String, Flow> allFlows = new ConcurrentHashMap<String, Flow>();
	/*
	 *  activeFlows stores the flow's status
	 *  when a flow is created, it will be inactive by default and set to false
	 *  only activate a flow when activateFlow API is called
	 */
	private static ConcurrentHashMap<String, Boolean> flowStatus = new ConcurrentHashMap<String, Boolean>();
	private static FlowModel flowMod = null;
	
	/**
	 * Adds a flow to the flow database.
	 * This function works with the /addFlow api to receive a flow from the user
	 * in JSON format and add to the database.
	 * @param flowModel	- json object of the user's flow input
	 * @return flowId
	 * @throws EventNotFoundException
	 */
	public static String addFlow(FlowModel flowModel) throws EventNotFoundException {
		Node head = new Node(null);
		flowMod = flowModel;
		head = addEventsToNode(flowModel.getHead(), head);
		for(String childNodeId: flowModel.getHead().childNodeIds) {
			addChildren(head, childNodeId);
		}
		String flowId = UUID.randomUUID().toString();
		while(allFlows.containsKey(flowId)) {
			flowId = UUID.randomUUID().toString();
		}
		Flow flow = new Flow(head, flowId);
		allFlows.put(flowId, flow);
		flowStatus.put(flowId, false);
		flowMod = null;
		return flowId;
	}
	
	private static Node addEventsToNode(NodeModel nodeModel, Node node) throws EventNotFoundException{
		for(String eventId: nodeModel.eventIds) {
			if(!EventHandlers.getBindEventsToDeviceMap().containsKey(eventId))
				throw new EventNotFoundException(eventId);
			node.addEvent(EventHandlers.bindEventsToDeviceMap.get(eventId));
		}
		return node;
	}
	
	private static Node addChildren(Node node, String childNodeId) throws EventNotFoundException {
		NodeModel nm = findChildFromInput(flowMod, childNodeId);
		if(nm == null) {
			System.out.println("addChildren() terminating at node: " + childNodeId + ". No further child nodes exist" );
			return node;
		}
		Node child = new Node(nm.nodeId);
		if(nm.childNodeIds.size() == 0) {
			child = addEventsToNode(nm, child);
			node.addChildren(child);
			return node;
		}
		child = addEventsToNode(nm, child);	
		for(String childNode: nm.childNodeIds) {
			addChildren(child, childNode);
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
	 */
	public static boolean activateFlow(String flowId) {
		if(flowStatus.replace(flowId, true) == null)
			return false;
		return true;
	}
	
	/**
	 * Marks a flow as inactive so that the flow can stop being processed.
	 * Inactive flows are still stored in database but just no longer processed.
	 * @param flowId
	 * @return boolean
	 */
	public static boolean deactivateFlow(String flowId) {
		if(flowStatus.replace(flowId, false) == null)
			return false;
		return true;
	}
	
	/**
	 * Removes a flow from the database.
	 * @param flowId
	 * @return String - success or error message
	 */
	public static String deleteFlow(String flowId) {
		if(allFlows.remove(flowId) == null) {
			return Constants.DOES_NOT_EXIST;
		}
		flowStatus.remove(flowId);
		return Constants.SUCCESS;
	}
	
	/**
	 * Returns all the flows that exist in database.
	 * @return HashMap - <flowId, Flow>
	 */
	public static ConcurrentHashMap<String, Flow> getAllFlows() {
		return allFlows;
	}
	
	/**
	 * Returns status of all the flows that exist in database.
	 * @return HashMap - <flowId, FlowStatus> 
	 * FlowStatus is either True(active) or False(inactive)
	 */
	public static ConcurrentHashMap<String, Boolean> getFlowStatuses() {
		return flowStatus;
	}
	
	/**
	 * Returns all the active flows that exist in database.
	 * @return List - <Flow>
	 */
	public static ConcurrentLinkedQueue<Flow> getActiveFlows() {
		ConcurrentLinkedQueue<Flow> result = new ConcurrentLinkedQueue<Flow>();
		for(Entry<String, Boolean> entry: flowStatus.entrySet()) {
			if(entry.getValue()) {
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
}
