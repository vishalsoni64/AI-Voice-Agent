package com.soni.ai_voice_agent.notification.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.soni.ai_voice_agent.notification.provider.ConsoleNotificationProvider;
import com.soni.ai_voice_agent.notification.provider.NotificationProvider;
import com.soni.ai_voice_agent.notification.provider.TwilioNotificationProvider;
import com.soni.ai_voice_agent.notification.client.TwilioApiCallClient;
import com.soni.ai_voice_agent.notification.client.TwilioCallClient;
@Configuration
public class NotificationProviderConfig {
	
	@Bean
	@ConditionalOnProperty(
			name = "notification.provider",
			havingValue = "console",
			matchIfMissing = true
			)
	public NotificationProvider consoleNotificationProvider() {
		return new ConsoleNotificationProvider();
	}
	
	@Bean
	@ConditionalOnProperty(
	        name = "notification.provider",
	        havingValue = "twilio"
	)
	public NotificationProvider twilioNotificationProvider(
	        TwilioConfig twilioConfig) {

	    TwilioCallClient twilioCallClient =
	            new TwilioApiCallClient(twilioConfig);

	    return new TwilioNotificationProvider(
	            twilioConfig,
	            twilioCallClient
	    );
	}
}
