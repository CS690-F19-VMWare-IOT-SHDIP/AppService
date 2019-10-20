package cs.usfca.edu.edgex.utils;

/**
 * The Flow class represents the workflow of the events.
 *
 */
public class Flow {
	private String flowId;
	private Node head;
	
	public Flow(Node head) {
		this.head = head;
	}
	
	/**
	 * This will execute the flow once all the events are 
	 * active for head node.
	 * @return
	 * @throws InterruptedException
	 */
	public boolean executeFlow() throws InterruptedException {
		while(!head.checkAllEvents()) {
			System.out.println("Waiting for flow to execute!");
			Thread.sleep(3000);
		}
		System.out.println("Executing flow!");
		boolean flag = true;
		for(Node child : head.getChildren()) {
			flag &= executeFlow(child);
		}
		return flag;
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