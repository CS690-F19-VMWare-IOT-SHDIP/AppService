package com.amazon.ask.alexaservlet.handlers;

import java.util.Date;
import java.util.Optional;
import static com.amazon.ask.request.Predicates.intentName;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import apis.AlexaClient;
import apis.AlexaModel;
import utils.AlexaCommands;

public class LedOnIntentHandler implements RequestHandler {
	String command = AlexaCommands.LED_ON;
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("LedOnIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		   String speechText = "Sure, I'll turn on the bulb";
		   AlexaModel alexaModel = new AlexaModel(command, new Date().getTime());
		   String response = AlexaClient.post(alexaModel);
		   if (response == null) {
			   speechText = "Sorry, I could not connect with the App Service";
		   }
		  return input.getResponseBuilder()
		           .withSpeech(speechText)
		           .withSimpleCard("IOT", speechText)
		           .build();
	}

}
