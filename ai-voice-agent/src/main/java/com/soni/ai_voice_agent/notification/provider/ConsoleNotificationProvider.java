package com.soni.ai_voice_agent.notification.provider;

import org.springframework.stereotype.Component;

import com.soni.ai_voice_agent.reminder.entity.Reminder;
import com.soni.ai_voice_agent.notification.provider.NotificationProvider;

@Component
public class ConsoleNotificationProvider implements NotificationProvider {

	public void sendReminderNotification(Reminder reminder) {
		System.out.println(
				"NOTIFICATION Reminder for task: "
				+ reminder.getTask().getTitle());
	}
}
