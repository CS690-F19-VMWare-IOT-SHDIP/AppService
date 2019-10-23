package cs.usfca.edu.edgex.model;

import java.util.List;

public class FlowModel {
	private NodeModel head;
	private List<NodeModel> children;
	
	public NodeModel getHead() {
		return this.head;
	}
	
	public List<NodeModel> getNodeList(){
		return this.children;
	}
}
