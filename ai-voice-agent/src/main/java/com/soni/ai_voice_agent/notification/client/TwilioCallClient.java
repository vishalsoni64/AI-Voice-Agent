package com.soni.ai_voice_agent.notification.client;

public interface TwilioCallClient {
	
	String createCall(
			
		String toNumber,
		String fromNumber,
		String twiml
		
			);
		
	}

