package com.amazon.ask.alexaservlet.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Date;
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import apis.AlexaClient;
import apis.AlexaModel;
import utils.AlexaCommands;

public class LedSeriesIntentHandler implements RequestHandler {
	String command = AlexaCommands.LED_SERIES; 
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName("LedSeriesIntent"));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		   String speechText = "Sure, let me switch on the led pattern";
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
