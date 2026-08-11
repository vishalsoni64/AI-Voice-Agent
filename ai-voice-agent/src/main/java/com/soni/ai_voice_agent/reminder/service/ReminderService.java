package com.soni.ai_voice_agent.reminder.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.soni.ai_voice_agent.entity.Task;
import com.soni.ai_voice_agent.reminder.entity.Reminder;
import com.soni.ai_voice_agent.reminder.entity.ReminderStatus;
import com.soni.ai_voice_agent.reminder.repository.ReminderRepository;

@Service /*@Service annotation indicates that this class is a service 
component in the Spring framework. It is used to define business logic and
 can be automatically detected and registered as a Spring bean during component 
 scanning. */
public class ReminderService {
	private final ReminderRepository reminderRepository;

	public ReminderService(ReminderRepository reminderRepository) {
		this.reminderRepository = reminderRepository;
	}

	// Add methods for creating, retrieving, updating, and deleting reminders

	public List<Reminder> findDueRemainders(){
		return reminderRepository.findByStatusAndReminderTimeBefore(
				ReminderStatus.PENDING, 
				LocalDateTime.now()
				);
	}
	
	public void processRemainder(Reminder reminder) {
		try {
			//mark the reminder as processing
			reminder.setStatus(ReminderStatus.PROCESSING);
			reminder.setAttemptCount(
					reminder.getAttemptCount()+1
					);
			reminderRepository.save(reminder);
			
			System.out.println("Processing reminder for task: "
								+ reminder.getTask().getTitle());
			//Temporary simulation of sending reminder
			//twilio will be added later
			reminder.setStatus(ReminderStatus.SENT);
			reminder.setProcessedAt(LocalDateTime.now());
			
			reminderRepository.save(reminder);
			
			System.out.println("Reminder sent successfully for task : " 
								+ reminder.getTask().getTitle());
		}catch (Exception e) {
			reminder.setStatus(ReminderStatus.FAILED);
			reminderRepository.save(reminder);
			System.out.println(
					"Failed to process reminder : " 
					+ e.getMessage()
					);
		}
	}
		
		public Reminder createReminder(Task task ) {
			Reminder reminder = new Reminder();
			reminder.setTask(task);
			reminder.setReminderTime(task.getReminderTime());
			reminder.setStatus(ReminderStatus.PENDING);
			reminder.setAttemptCount(0);
			
			return reminderRepository.save(reminder);
			
		
	}
}
