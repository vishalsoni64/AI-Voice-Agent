package com.soni.ai_voice_agent.notification.service;

import org.springframework.stereotype.Service;

import com.soni.ai_voice_agent.reminder.entity.Reminder;

@Service
public class NotificationService {
	
	public void sendReminderNotification(Reminder reminder) {
		
		System.out.println(
				"NOTIFICATION Reminder for tasks: " 
				+ reminder.getTask().getTitle()
				);
	
	}

}
