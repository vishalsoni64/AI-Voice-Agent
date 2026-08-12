package com.soni.ai_voice_agent.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.soni.ai_voice_agent.reminder.service.ReminderService;
import com.soni.ai_voice_agent.reminder.entity.Reminder;


@Component
public class RemainderScheduler {
	
	private final ReminderService reminderService;
	
	public RemainderScheduler(ReminderService reminderService) {
		this.reminderService = reminderService;
	}
	
	@Scheduled(fixedRate = 10000)	
	// This method will be executed every 10 seconds
	public void processDueReminders() {
		
		System.out.println("Checking for due reminders......");
		
		List<Reminder> reminders = reminderService.findDueReminders();
		
		for(Reminder reminder : reminders) {
			boolean claimed = 
					reminderService.claimReminder(reminder.getId());
			if(claimed) {
				
				reminderService.processReminder(reminder);
				
			}else {
				
				System.out.println(
						"Reminder " + reminder.getId()
						+ " was already claimed");
			}
		}
	}

}
