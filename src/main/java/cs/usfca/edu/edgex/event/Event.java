package cs.usfca.edu.edgex.event;

public interface Event {
	public boolean isActive();
	public void trigger();
}
