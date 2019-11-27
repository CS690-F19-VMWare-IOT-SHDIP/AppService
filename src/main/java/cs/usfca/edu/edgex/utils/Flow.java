package cs.usfca.edu.edgex.utils;


/**
 * The Flow class represents the workflow of the events.
 *
 */
public class Flow {
	private String flowId;
	private Node head;
	private boolean status;
	
	public Flow(Node head, String flowId) {
		this.head = head;
		this.flowId = flowId;
		this.status = false;
	}
	
	/**
	 * Returns flow Id of the flow.
	 * @return String
	 */
	public String getFlowId() {
		return this.flowId;
	}
	
	/**
	 * Returns the head node of the flow.
	 * @return Node
	 */
	public Node getHead() {
		return head;
	}
	
	/**
	 * Returns true if the flow is active and returns false if
	 * the flow is inactive.
	 * @return boolean
	 */
	public boolean isActive() {
		return status;
	}
	
	/**
	 * This method is used to activate a flow.
	 */
	public void activate() {
		this.status = true;
	}
	
	/**
	 * This method is used to deActivate a flow.
	 */
	public void deActivate() {
		this.status = false;
	}
	
	/**
	 * This will execute the flow once all the events are 
	 * active for head node.
	 * @return
	 * @throws InterruptedException
	 */
	public boolean executeFlow() throws InterruptedException {
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