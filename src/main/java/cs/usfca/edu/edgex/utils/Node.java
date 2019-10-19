package cs.usfca.edu.edgex.utils;

import java.util.LinkedList;
import java.util.List;

import cs.usfca.edu.edgex.event.Event;

public class Node {
	private String nodeId;
	private List<Event> events;
	private List<Node> children;
	
	public Node() {
		this.events = new LinkedList<Event>();
		this.children = new LinkedList<Node>();
	}
	
	public void addEvent(Event event) {
		if(!events.contains(event)) {
			events.add(event);
		}
	}
	
	public boolean removeEvent(Event event) {
		if(events.contains(event)) {
			return events.remove(event);
		}
		return false;
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	public void addChildren(Node node) {
		if(!children.contains(node)) {
			children.add(node);
		}
	}
	
	public boolean removeChildren(Node node) {
		if(children.contains(node)) {
			return children.remove(node);
		}
		return false;
	}
	
	public boolean triggerAllEvents() {
		for(Event i : events) {
			i.trigger();
		}
		return checkAllEvents();
	}
	
	public boolean checkAllEvents() {
		for(Event i : events) {
			if(!i.isActive()) {
				return false;
			}
		}
		return true;
	}
}
