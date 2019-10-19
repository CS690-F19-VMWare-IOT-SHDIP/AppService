package cs.usfca.edu.edgex.event.adhocevent;

import java.util.Random;

import cs.usfca.edu.edgex.event.Event;

public class RandomModEvent implements Event {
	
	private Random random;
	private int n;
	
	public RandomModEvent(int n) {
		this.n = n;
		this.random = new Random();
	}
	
	public boolean isActive() {
		boolean flag = (random.nextInt() % n == 0);
		System.out.println("RandomModEvent is: " + flag);
		return flag;
	}

	public void trigger() {
		System.out.println("RandomModEvent doesn't support trigger!");
	}
	
}
