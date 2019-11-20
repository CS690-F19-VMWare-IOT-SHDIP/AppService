package cs.usfca.edu.edgex.utils;

import java.util.LinkedList;
import java.util.List;

import cs.usfca.edu.edgex.event.Event;

/**
 * Node is a data type used to represent
 * node. 
 */
public class Node {
	private String nodeId;
	private List<Event> events;
	private List<Node> children;
	
	public Node(String nodeId) {
		this.events = new LinkedList<Event>();
		this.children = new LinkedList<Node>();
		this.nodeId = nodeId;
	}
	
	/**
	 * Returns node id of the node.
	 * @return String
	 */
	public String getNodeId() {
		return nodeId;
	}
	
	/**
	 * Returns list of events present in the node.
	 * @return List<Event>
	 */
	public List<Event> getEvents() {
		return events;
	}
	
	/**
	 * Add event to given node.
	 * @param event
	 */
	public void addEvent(Event event) {
		if(!events.contains(event)) {
			events.add(event);
		}
	}
	
	/**
	 * Remove event from current node.
	 * @param event
	 * @return boolean
	 */
	public boolean removeEvent(Event event) {
		if(events.contains(event)) {
			return events.remove(event);
		}
		return false;
	}
	
	/**
	 * Get list of all children nodes.
	 * @return List<Node>
	 */
	public List<Node> getChildren() {
		return new LinkedList<Node>(children);
	}
	
	/**
	 * Adds a child node to given node.
	 * @param node
	 */
	public void addChildren(Node node) {
		if(!children.contains(node)) {
			children.add(node);
		}
	}
	
	/**
	 * Remvoes a child node from given node.
	 * @param node
	 * @return boolean
	 */
	public boolean removeChildren(Node node) {
		if(children.contains(node)) {
			return children.remove(node);
		}
		return false;
	}
	
	/**
	 * Triggers all events on given node and
	 * returns true if all the events are 
	 * successfully executed.
	 * @return boolean
	 */
	public boolean triggerAllEvents() {
		for(Event i : events) {
			i.trigger();
		}
		return checkAllEvents();
	}
	
	/**
	 * Check if all the events are true
	 * for given node.
	 * @return boolean
	 */
	public boolean checkAllEvents() {
		for(Event i : events) {
			if(!i.isActive()) {
				return false;
			}
		}
		return true;
	}
}
