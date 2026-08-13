package com.soni.ai_voice_agent.reminder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.soni.ai_voice_agent.entity.Task;
import com.soni.ai_voice_agent.notification.service.NotificationService;
import com.soni.ai_voice_agent.reminder.entity.Reminder;
import com.soni.ai_voice_agent.reminder.entity.ReminderStatus;
import com.soni.ai_voice_agent.reminder.repository.ReminderRepository;

public class ReminderServiceTest {
	@Mock
	private ReminderRepository reminderRepository;
	@Mock
	private NotificationService notificationService;
	@InjectMocks
	private ReminderService reminderService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	public Reminder createReminder(Task task) {
	    Reminder reminder = new Reminder();

	    reminder.setTask(task);
	    reminder.setReminderTime(task.getReminderTime());
	    reminder.setStatus(ReminderStatus.PENDING);
	    reminder.setAttemptCount(0);

	    return reminderRepository.save(reminder);
	}
	
	@Test
	void shouldCreateReminderForTask() {

	    Task task = new Task();

	    task.setId(1L);
	    task.setTitle("Learn Spring Boot");

	    LocalDateTime reminderTime =
	            LocalDateTime.of(2026, 8, 15, 10, 0);

	    task.setReminderTime(reminderTime);

	    when(reminderRepository.save(any(Reminder.class)))
	            .thenAnswer(invocation -> invocation.getArgument(0));

	    Reminder result = reminderService.createReminder(task);

	    assertNotNull(result);

	    assertEquals(task, result.getTask());

	    assertEquals(
	            reminderTime,
	            result.getReminderTime()
	    );

	    assertEquals(
	            ReminderStatus.PENDING,
	            result.getStatus()
	    );

	    assertEquals(
	            0,
	            result.getAttemptCount()
	    );

	    verify(reminderRepository).save(any(Reminder.class));
	}
	
	@Test
	void shouldUpdateReminderTime() {

	    Task task = new Task();

	    task.setId(1L);

	    LocalDateTime newTime =
	            LocalDateTime.of(2026, 8, 16, 11, 30);

	    task.setReminderTime(newTime);

	    Reminder reminder = new Reminder();

	    reminder.setReminderTime(
	            LocalDateTime.of(2026, 8, 15, 10, 0)
	    );

	    reminder.setStatus(ReminderStatus.PENDING);

	    when(reminderRepository.findByTaskId(1L))
	            .thenReturn(Optional.of(reminder));

	    reminderService.updateReminderTime(task);

	    assertEquals(
	            newTime,
	            reminder.getReminderTime()
	    );

	    assertEquals(
	            ReminderStatus.PENDING,
	            reminder.getStatus()
	    );

	    verify(reminderRepository).save(reminder);
	}
	
	@Test
	void shouldNotChangeSentReminderToPending() {

	    Task task = new Task();

	    task.setId(2L);

	    task.setReminderTime(
	            LocalDateTime.of(2026, 8, 17, 12, 0)
	    );

	    Reminder reminder = new Reminder();

	    reminder.setStatus(ReminderStatus.SENT);

	    when(reminderRepository.findByTaskId(2L))
	            .thenReturn(Optional.of(reminder));

	    reminderService.updateReminderTime(task);

	    assertEquals(
	            ReminderStatus.SENT,
	            reminder.getStatus()
	    );

	    assertEquals(
	            task.getReminderTime(),
	            reminder.getReminderTime()
	    );

	    verify(reminderRepository).save(reminder);
	}
	
	@Test
	void shouldCancelPendingReminder() {

	    Reminder reminder = new Reminder();

	    reminder.setStatus(ReminderStatus.PENDING);

	    when(reminderRepository.findByTaskId(1L))
	            .thenReturn(Optional.of(reminder));

	    reminderService.cancelReminderForTask(1L);

	    assertEquals(
	            ReminderStatus.CANCELLED,
	            reminder.getStatus()
	    );

	    verify(reminderRepository).save(reminder);
	}
	
	@Test
	void shouldNotCancelSentReminder() {

	    Reminder reminder = new Reminder();

	    reminder.setStatus(ReminderStatus.SENT);

	    when(reminderRepository.findByTaskId(1L))
	            .thenReturn(Optional.of(reminder));

	    reminderService.cancelReminderForTask(1L);

	    assertEquals(
	            ReminderStatus.SENT,
	            reminder.getStatus()
	    );

	    verify(reminderRepository, never())
	            .save(any(Reminder.class));
	}
	
	@Test
	void shouldDeleteReminderForTask() {

	    Reminder reminder = new Reminder();

	    when(reminderRepository.findByTaskId(1L))
	            .thenReturn(Optional.of(reminder));

	    reminderService.deleteReminderForTask(1L);

	    verify(reminderRepository)
	            .delete(reminder);
	}
	
}
