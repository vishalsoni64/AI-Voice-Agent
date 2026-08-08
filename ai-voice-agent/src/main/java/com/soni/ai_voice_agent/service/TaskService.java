package com.soni.ai_voice_agent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soni.ai_voice_agent.repository.TaskRepository;
import com.soni.ai_voice_agent.dto.TaskRequest;
import com.soni.ai_voice_agent.entity.Task;

@Service
public class TaskService {
	
	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	 public Task createTask(TaskRequest request) {

	        Task task = new Task();

	        task.setTitle(request.getTitle());
	        task.setDescription(request.getDescription());
	        task.setReminderTime(request.getReminderTime());
	        task.setStatus(request.getStatus());

	        return taskRepository.save(task);
	}
	 
	 public List<Task> getAllTasks() {
	        return taskRepository.findAll();
	 }

	

}
