package cs.usfca.edu.edgex.utils;

import cs.usfca.edu.edgex.apis.flowapis.FlowHandlers;

/**
 * The Flow class represents the workflow of the events.
 *
 */
public class Flow {
	private String flowId;
	private Node head;
	
	public Flow(Node head, String flowId) {
		this.head = head;
		this.flowId = flowId;
	}
	
	public String getFlowId() {
		return this.flowId;
	}
	
	/**
	 * This will execute the flow once all the events are 
	 * active for head node.
	 * @return
	 * @throws InterruptedException
	 */
	public boolean executeFlow() throws InterruptedException {
//		while(!head.checkAllEvents()) {
//			System.out.println("Waiting for flow to execute!");
//			Thread.sleep(3000);
//		}
		if(head.checkAllEvents()) {
			System.out.println("Executing flow with flowId: " + flowId.substring(5));
			boolean flag = true;
			for(Node child : head.getChildren()) {
				flag &= executeFlow(child);
			}
			return flag;
		}
		return false;
	}
	
	/**
	 * Execute flow helper.
	 * @param node
	 * @return
	 */
	private boolean executeFlow(Node node) {
		boolean flag = node.triggerAllEvents();
		if(flag) {
			for(Node child : node.getChildren()) {
				flag &= executeFlow(child);
			}
		}
		return flag;
	}
}