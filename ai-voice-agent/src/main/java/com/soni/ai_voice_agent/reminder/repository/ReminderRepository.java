package com.soni.ai_voice_agent.reminder.repository;

import java.time.LocalDateTime;
import java.util.List;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soni.ai_voice_agent.reminder.entity.Reminder;
import com.soni.ai_voice_agent.reminder.entity.ReminderStatus;

public interface ReminderRepository extends JpaRepository<Reminder, Long>{
	
	List<Reminder> findByStatusAndReminderTimeLessThanEqual(
			ReminderStatus status, 
			LocalDateTime time
			);
	
	Optional<Reminder> findByTaskId(Long taskId);
	
	
	@Modifying
	@Query("""
			UPDATE Reminder r
			SET r.status = :processingStatus
			WHERE r.id = :id
			AND r.status = :pendingStatus
			 	""")
	int claimReminder(
			@Param("id") Long id,
			@Param("pendingStatus") ReminderStatus pendingStatus,
			@Param("processingStatus") ReminderStatus processingStatus
			);
}
