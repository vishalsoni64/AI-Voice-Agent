package com.soni.ai_voice_agent.reminder.service;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soni.ai_voice_agent.entity.Task;
import com.soni.ai_voice_agent.notification.service.NotificationService;
import com.soni.ai_voice_agent.reminder.entity.Reminder;
import com.soni.ai_voice_agent.reminder.entity.ReminderStatus;
import com.soni.ai_voice_agent.reminder.repository.ReminderRepository;

@Service /*@Service annotation indicates that this class is a service 
component in the Spring framework. It is used to define business logic and
 can be automatically detected and registered as a Spring bean during component 
 scanning. */
public class ReminderService {
	
	private final ReminderRepository reminderRepository;
	private final NotificationService notificationService;

	public ReminderService(ReminderRepository reminderRepository,
			NotificationService notificationService) {
		this.reminderRepository = reminderRepository;
		this.notificationService = notificationService;
	}

	// Add methods for creating, retrieving, updating, and deleting reminders
	public List<Reminder> findDueReminders(){
		return reminderRepository
				.findByStatusAndReminderTimeLessThanEqual(
				ReminderStatus.PENDING, 
				LocalDateTime.now()
				);
	}
	
	public void processReminder(Reminder reminder) {
		try {
			//mark the reminder as processing
			/*reminder.setStatus(ReminderStatus.PROCESSING);
			reminder.setAttemptCount(
					reminder.getAttemptCount()+1
					);
			reminderRepository.save(reminder);
			*/
			System.out.println("Processing reminder for task: "
								+ reminder.getTask().getTitle());
						
			reminder.setAttemptCount(
					reminder.getAttemptCount() + 1);
			
			reminderRepository.save(reminder);
			
			//Send notification
			notificationService.sendReminderNotification(reminder);

			
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
		
		
		public void updateReminderTime(Task task) {
			Optional<Reminder> optionalReminder = 
					reminderRepository.findByTaskId(task.getId());
			
			if(optionalReminder.isPresent()) {
				Reminder reminder = optionalReminder.get();
				
				reminder.setReminderTime(task.getReminderTime());
				
				if(reminder.getStatus() != ReminderStatus.SENT) {
					reminder.setStatus(ReminderStatus.PENDING);
				}
				
				reminderRepository.save(reminder);
			}
		}
		
		
		public void cancelReminderForTask(Long taskId) {
			Optional<Reminder> optionalReminder = 
					reminderRepository.findByTaskId(taskId);
			if(optionalReminder.isPresent()) {
				Reminder reminder = optionalReminder.get();
				
				if(reminder.getStatus() != ReminderStatus.SENT) {
					reminder.setStatus(ReminderStatus.CANCELLED);
					reminderRepository.save(reminder);
				}
			}	
		}
		
		
		public void deleteReminderForTask(Long taskId) {

		    Optional<Reminder> optionalReminder =
		            reminderRepository.findByTaskId(taskId);

		    if (optionalReminder.isPresent()) {

		        Reminder reminder = optionalReminder.get();

		        reminderRepository.delete(reminder);
		    }
		}
		
		@Transactional
		public boolean claimReminder(Long reminderId) {
			int updatedRows = reminderRepository.claimReminder(
					reminderId, ReminderStatus.PENDING,ReminderStatus.PROCESSING
					);
			return updatedRows == 1;
					
		}
}
