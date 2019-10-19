package cs.usfca.edu.edgex.utils;

public class Flow {
	private String flowId;
	private Node head;
	
	public Flow(Node head) {
		this.head = head;
	}
	
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