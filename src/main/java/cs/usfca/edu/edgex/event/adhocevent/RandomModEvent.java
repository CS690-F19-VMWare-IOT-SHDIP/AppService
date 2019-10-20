package cs.usfca.edu.edgex.event.adhocevent;

import java.util.Random;

import cs.usfca.edu.edgex.event.Event;

/**
 * It generates a random number and calculates
 * mod with given value. If the remainder is 0
 * then the event is true.
 */
public class RandomModEvent implements Event {
	
	private Random random;
	private int n;
	
	/**
	 * RandomModEvent constructor that takes n.
	 * @param n
	 */
	public RandomModEvent(int n) {
		this.n = n;
		this.random = new Random();
	}
	
	/**
	 * Checks if the random mod event is true or not.
	 * @return boolean
	 */
	public boolean isActive() {
		boolean flag = (random.nextInt() % n == 0);
		System.out.println("RandomModEvent is: " + flag);
		return flag;
	}
	
	/**
	 * Trigger is disabled for this event.
	 */
	public void trigger() {
		System.out.println("RandomModEvent doesn't support trigger!");
	}
	
}
