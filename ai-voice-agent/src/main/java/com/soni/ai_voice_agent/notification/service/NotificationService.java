package com.soni.ai_voice_agent.notification.service;

import org.springframework.stereotype.Service;

import com.soni.ai_voice_agent.notification.provider.NotificationProvider;
import com.soni.ai_voice_agent.reminder.entity.Reminder;

@Service
public class NotificationService {
	
	private final NotificationProvider notificationProvider;
	
	public NotificationService(NotificationProvider notificationProvider) {
		this.notificationProvider = notificationProvider; 
	}
	
	public void sendReminderNotification(Reminder reminder) {
		
		notificationProvider.sendReminderNotification(reminder);
	
	}

}
