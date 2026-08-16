package com.soni.ai_voice_agent.notification.client;

import com.soni.ai_voice_agent.notification.config.TwilioConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;

public class TwilioApiCallClient implements TwilioCallClient{
	
	private final TwilioConfig twilioconfig;
	
	public TwilioApiCallClient(TwilioConfig twilioConfig) {
		this.twilioconfig =twilioConfig;
	}
	
	
	public String createCall(
			String toNumber,
			String fromNumber,
			String twiml) {
		Twilio.init(twilioconfig.getAccountSid(),
				twilioconfig.getAuthToken());
		
		Call call = Call.creator(new PhoneNumber(toNumber),
				new PhoneNumber(fromNumber),
				new Twiml(twiml))
				.create();
		
		return call.getSid();
	}
	
	
	

}
