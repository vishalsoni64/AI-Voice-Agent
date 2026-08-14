package com.soni.ai_voice_agent.notification.provider;

import com.soni.ai_voice_agent.reminder.entity.Reminder;

public interface NotificationProvider {

	void sendReminderNotification(Reminder reminder);
}
