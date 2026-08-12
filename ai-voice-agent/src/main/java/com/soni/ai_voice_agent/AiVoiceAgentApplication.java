package com.soni.ai_voice_agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling
public class AiVoiceAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiVoiceAgentApplication.class, args);
	}

}
