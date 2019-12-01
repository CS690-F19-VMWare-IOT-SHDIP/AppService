/*
Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
except in compliance with the License. A copy of the License is located at

    http://aws.amazon.com/apache2.0/

or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
the specific language governing permissions and limitations under the License.
*/

package com.amazon.ask.alexaservlet.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import apis.AlexaClient;
import apis.AlexaModel;
import utils.AlexaCommands;

import java.util.Date;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class FanIntentHandler implements RequestHandler {
	String command = AlexaCommands.FAN;
	@Override
	public boolean canHandle(HandlerInput input) {
	   return input.matches(intentName("FanIntent"));
	}
	
	@Override
	public Optional<Response> handle(HandlerInput input) {
	   String speechText = "Sure, let me switch on the fan";
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
