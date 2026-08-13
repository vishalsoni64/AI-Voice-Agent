package com.soni.ai_voice_agent.scheduler;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.soni.ai_voice_agent.reminder.entity.Reminder;
import com.soni.ai_voice_agent.reminder.service.ReminderService;
import com.soni.ai_voice_agent.*;

public class ReminderSchedulerTest {
	@Mock
	private ReminderService reminderService;
	@InjectMocks
	private ReminderScheduler reminderScheduler;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	void shouldProcessDueReminderWhenClaimSucceeds() {

	    Reminder reminder = new Reminder();
	    reminder.setId(1L);

	    when(reminderService.findDueReminders())
	            .thenReturn(List.of(reminder));

	    when(reminderService.claimReminder(1L))
	            .thenReturn(true);

	    reminderScheduler.processDueReminders();

	    verify(reminderService)
	            .findDueReminders();

	    verify(reminderService)
	            .claimReminder(1L);

	    verify(reminderService)
	            .processReminder(reminder);
	}
	
	@Test
	void shouldNotProcessReminderWhenClaimFails() {

	    Reminder reminder = new Reminder();
	    reminder.setId(2L);

	    when(reminderService.findDueReminders())
	            .thenReturn(List.of(reminder));

	    when(reminderService.claimReminder(2L))
	            .thenReturn(false);

	    reminderScheduler.processDueReminders();

	    verify(reminderService)
	            .findDueReminders();

	    verify(reminderService)
	            .claimReminder(2L);

	    verify(reminderService, never())
	            .processReminder(reminder);
	}

}
