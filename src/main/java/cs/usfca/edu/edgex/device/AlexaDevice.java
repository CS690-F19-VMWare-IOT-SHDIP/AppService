package cs.usfca.edu.edgex.device;

import cs.usfca.edu.edgex.edgexclient.AlexaClient;
import cs.usfca.edu.edgex.edgexclient.SlackClient;
import cs.usfca.edu.edgex.exceptions.InvalidInputException;
import cs.usfca.edu.edgex.model.AlexaModel;
import cs.usfca.edu.edgex.model.Model;
import cs.usfca.edu.edgex.model.SlackResponse;
import cs.usfca.edu.edgex.utils.AlexaCommands;
import cs.usfca.edu.edgex.utils.URLPaths;

/**
 * Support virtual device Alexa.
 */
public class AlexaDevice implements Device<AlexaModel> {
	
	private AlexaModel alexa;
	private DeviceType deviceType = DeviceType.VIRTUAL;
	private String slackMessage = "BULB_ON";
	
	public AlexaDevice(Model input) throws InvalidInputException {
		if(input instanceof AlexaModel) {
			this.alexa = (AlexaModel) input;
		}
		else {
			throw new InvalidInputException("Wrong object passed in to AlexaDevice");
		}
	}

	@Override
	public AlexaModel get() {
		float latestTimestamp = AlexaClient.getTimeStamp(alexa.getMessageType());
		if (latestTimestamp != 0) {
			alexa.setTimeStamp(latestTimestamp);
			System.out.println("!!!!!***** Got new message from Alexa skill client for Alexa device");
			if(AlexaCommands.SlackIntegrated.contains(alexa.getMessageType())) {
				SlackResponse response = new SlackClient().post(URLPaths.token, URLPaths.channelID, slackMessage);
				if(response.getOk() != true) {
					System.out.println("Could not post messsage to slack " + response.getError());
				}
			} 
			return alexa;
		}
		return null;
	}

	@Override
	public void set(AlexaModel val) {
		System.out.println("Unimplemented Method set on AlexaDevice");
	}

	@Override
	public DeviceType getDeviceType() {
		return this.deviceType;
	}

	@Override
	public Object getDevice() {
		return alexa;
	}

	@Override
	public boolean equals(Device<?> device) {
		if(device.getDevice() instanceof AlexaModel) {
			AlexaDevice received = (AlexaDevice) device;
			if(received.alexa.getMessageType().equals(alexa.getMessageType()) && received.alexa.getTimestamp() == alexa.getTimestamp()) {
				return true;
			}
		}
		return false;
	}
	

}
