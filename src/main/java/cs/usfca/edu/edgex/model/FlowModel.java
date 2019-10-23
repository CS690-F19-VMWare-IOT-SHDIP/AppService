package cs.usfca.edu.edgex.model;

import java.util.List;

public class FlowModel {
	/*
	 * made all the members public so they are accessible
	 * by Spring Json serialization framework
	 */
	public NodeModel head;
	public List<NodeModel> children;
	
	public NodeModel getHead() {
		return this.head;
	}
	
	public List<NodeModel> getNodeList(){
		return this.children;
	}
}
