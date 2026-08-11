package com.soni.ai_voice_agent.reminder.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soni.ai_voice_agent.reminder.entity.Reminder;
import com.soni.ai_voice_agent.reminder.entity.ReminderStatus;

public interface ReminderRepository extends JpaRepository<Reminder, Long>{
	
	List<Reminder> findByStatusAndReminderTimeBefore(
			ReminderStatus status, 
			LocalDateTime time);
}
