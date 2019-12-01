/*
Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
except in compliance with the License. A copy of the License is located at

    http://aws.amazon.com/apache2.0/

or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
the specific language governing permissions and limitations under the License.
*/

package com.amazon.ask.alexaservlet;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;

import com.amazon.ask.alexaservlet.handlers.CancelandStopIntentHandler;
import com.amazon.ask.alexaservlet.handlers.FanIntentHandler;
import com.amazon.ask.alexaservlet.handlers.GreetingsHandler;
import com.amazon.ask.alexaservlet.handlers.HelpIntentHandler;
import com.amazon.ask.alexaservlet.handlers.SessionEndedRequestHandler;
import com.amazon.ask.alexaservlet.handlers.LaunchRequestHandler;
import com.amazon.ask.alexaservlet.handlers.LedOffIntentHandler;
import com.amazon.ask.alexaservlet.handlers.LedOnIntentHandler;
import com.amazon.ask.alexaservlet.handlers.LedSeriesIntentHandler;

public class AlexaSkillServlet extends SkillServlet {

	public AlexaSkillServlet() {
	   super(getSkill());
	}
	
	private static Skill getSkill() {
	   return Skills.standard()
	           .addRequestHandlers(
	                   new CancelandStopIntentHandler(),
	                   new GreetingsHandler(),
	                   new FanIntentHandler(),
	                   new LedOffIntentHandler(),
	                   new LedOnIntentHandler(),
	                   new LedSeriesIntentHandler(),
	                   new HelpIntentHandler(),
	                   new LaunchRequestHandler(),
	                   new SessionEndedRequestHandler())
	           .withSkillId("amzn1.ask.skill.4cb6125d-6722-4567-ab6a-072ba38e564a")
	           .build();
	}

}
