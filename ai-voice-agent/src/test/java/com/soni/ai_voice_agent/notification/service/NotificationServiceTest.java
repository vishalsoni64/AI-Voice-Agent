package com.soni.ai_voice_agent.notification.service;
import com.soni.ai_voice_agent.notification.provider.ConsoleNotificationProvider;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.soni.ai_voice_agent.entity.Task;
import com.soni.ai_voice_agent.notification.provider.ConsoleNotificationProvider;
import com.soni.ai_voice_agent.reminder.entity.Reminder;

public class NotificationServiceTest {

	private NotificationService notificationService;
	
	@BeforeEach
	void setUp() {
		
		notificationService = 
				new NotificationService(
						new ConsoleNotificationProvider()
						);
	}
	
    @Test
    void shouldSendReminderNotification() {

        Task task = new Task();
        task.setTitle("Notification Test");

        Reminder reminder = new Reminder();
        reminder.setTask(task);

        assertDoesNotThrow(() ->
                notificationService.sendReminderNotification(reminder)
        );
    }
	
}
