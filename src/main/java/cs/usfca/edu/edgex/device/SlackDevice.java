package cs.usfca.edu.edgex.device;

import cs.usfca.edu.edgex.edgexclient.SlackClient;
import cs.usfca.edu.edgex.exceptions.InvalidInputException;
import cs.usfca.edu.edgex.model.Model;
import cs.usfca.edu.edgex.model.SlackInput;
import cs.usfca.edu.edgex.model.SlackResponse;

public class SlackDevice implements Device<SlackResponse> {
	private SlackInput input;
	private static String oldestMsgTs = "0";
	/**
	 * Constructor for creating SlackDevice.
	 * @param input
	 */
	public SlackDevice(Model input) throws InvalidInputException {
		if(input instanceof SlackInput) {
			this.input = (SlackInput)input;
		}
		else {
			throw new InvalidInputException("Wrong object passed in to SlackDevice");
		}
	}
	
	/**
	 * Gets the next oldest message in the channel's conversation history
	 * return String
	 */
	@Override
	public SlackResponse get() {
		// TODO Implement logic to the next oldest message in the channel's conversation history
		SlackResponse response = new SlackClient().get(input.getToken(), input.getChannel(), oldestMsgTs);
		String currentTs = response.getTs();
		if(oldestMsgTs.equals(currentTs)) {
			/*
			 *  if there's no newer message after the last processed one,
			 *  return null to avoid processing the same message repeatedly.
			 */
			return null;
		}
		oldestMsgTs = response.getTs();
		return response;
	}

	@Override
	public void set(SlackResponse val) {
		// TODO Auto-generated method stub
		System.out.println("Unimplemented Method set on Device VirtualRandomModDevice");
	}

	/**
	 * Returns the current deviceType which is virtual.
	 * @return DeviceType
	 */
	@Override
	public DeviceType getDeviceType() {
		return DeviceType.VIRTUAL;
	}

	/**
	 * Returns the device input model to compare with other devices.
	 * @return Object
	 */
	@Override
	public Object getDevice() {
		return input;
	}
	
	public SlackInput getInput() {
		return this.input;
	}
	
	/**
	 * Equals method to compare current device with other deviceTypes.
	 * @param device
	 * @return boolean
	 */
	@Override
	public boolean equals(Device<?> device) {
		if(device.getDevice() instanceof SlackInput) {
			SlackInput received = (SlackInput) device.getDevice();
			if(received.getChannel().equals(input.getChannel())) {
				return true;
			}
		}
		return false;
	}

}
