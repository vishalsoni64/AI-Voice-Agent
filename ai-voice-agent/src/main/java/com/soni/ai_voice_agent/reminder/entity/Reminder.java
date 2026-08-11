package com.soni.ai_voice_agent.reminder.entity;

import java.time.LocalDateTime;

import com.soni.ai_voice_agent.entity.Task;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "reminders")
public class Reminder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime reminderTime;
	
	@Enumerated(EnumType.STRING)
	private ReminderStatus status;
	
	private Integer attemptCount;
	private LocalDateTime processedAt;
	private LocalDateTime createdAt;
	private LocalDateTime updateAt;
	
	@ManyToOne
	@JoinColumn(name = "task_id", nullable = false)
	private Task task;

	
	public Reminder() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(LocalDateTime reminderTime) {
		this.reminderTime = reminderTime;
	}

	public ReminderStatus getStatus() {
		return status;
	}

	public void setStatus(ReminderStatus status) {
		this.status = status;
	}

	public Integer getAttemptCount() {
		return attemptCount;
	}

	public void setAttemptCount(Integer attemptCount) {
		this.attemptCount = attemptCount;
	}

	public LocalDateTime getProcessedAt() {
		return processedAt;
	}

	public void setProcessedAt(LocalDateTime processedAt) {
		this.processedAt = processedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updateAt = LocalDateTime.now();
		
		if(attemptCount == null) {
			attemptCount = 0;
		}	
		if(status == null) {
			status = ReminderStatus.PENDING;
		}
	}
	
	@PreUpdate
	protected void onUpdate() {
		updateAt = LocalDateTime.now();
	
	}
}

