package cs.usfca.edu.edgex.event;

import cs.usfca.edu.edgex.device.Device;
import cs.usfca.edu.edgex.device.DeviceType;
import cs.usfca.edu.edgex.device.SlackDevice;
import cs.usfca.edu.edgex.exceptions.UnsupportedDeviceTypeException;
import cs.usfca.edu.edgex.model.SlackResponse;

/**
 * This gets a message from a Slack channel 
 * or sends a message to a slack channel
 */
public class SlackEvent implements Event {

	private static DeviceType deviceType = DeviceType.VIRTUAL;
	private Device<SlackResponse> device;
	
	/**
	 * SlackEvent constructor that takes a Slack device.
	 * @param Slack device
	 * @throws UnsupportedDeviceTypeException 
	 */
	public SlackEvent(Device<SlackResponse> device) throws UnsupportedDeviceTypeException {
		if(device.getDeviceType() != deviceType) {
			throw new UnsupportedDeviceTypeException(device.getDeviceType(), DeviceType.VIRTUAL);
		}
		this.device = device;
	}
	
	/**
	 * Checks if the current slack message being processed 
	 * matches any of the configured slack commands 
	 * @return boolean
	 */
	@Override
	public boolean isActive() {
		if(this.device instanceof SlackDevice) {
			SlackDevice slackdevice = (SlackDevice) this.device;
			SlackResponse slackResp = slackdevice.get();
			if(slackdevice.getInput().getCommand() == null || slackdevice.getInput().getCommand().isEmpty()
					|| slackResp == null) {
				return false;
			}
			String msgTxt = slackResp.getMessageText();
			System.out.println("Slack Event: Text Received: " + msgTxt);
			System.out.println("Slack Device Input: " + slackdevice.getInput().getCommand());
			System.out.println("Slack Event: isActive returns: " + slackdevice.getInput().getCommand().trim().equals(msgTxt));
			return slackdevice.getInput().getCommand().trim().equals(msgTxt);
		}
		return false;
	}
	
	/**
	 * Trigger is not implemented for this event.
	 */
	@Override
	public void trigger() {
		// TODO Auto-generated method stub
		System.out.println("Unimplemented method trigger for Slack Device");
	}

	/**
	 * Static method to identify DeviceType that
	 * this event supports.
	 * @return Devicetype
	 */
	public static DeviceType getDeviceType() {
		return deviceType;
	}
	
	@Override
	public Device<?> getDevice() {
		// TODO Auto-generated method stub
		return this.device;
	}

	@Override
	public boolean equals(Event event) {
		// TODO Auto-generated method stub
		return (this.getClass().getSimpleName().equals(event.getClass().getSimpleName()) 
				&& this.device.equals(event.getDevice()));
	}

}
