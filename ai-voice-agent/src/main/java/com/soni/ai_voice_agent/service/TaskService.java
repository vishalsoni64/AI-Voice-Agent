package com.soni.ai_voice_agent.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soni.ai_voice_agent.repository.TaskRepository;
import com.soni.ai_voice_agent.dto.TaskRequest;
import com.soni.ai_voice_agent.entity.Task;
import com.soni.ai_voice_agent.exception.TaskNotFoundException;
import com.soni.ai_voice_agent.reminder.service.ReminderService;

@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	private final ReminderService reminderService;

	public TaskService(TaskRepository taskRepository,ReminderService reminderService) {
		this.taskRepository = taskRepository;
		this.reminderService = reminderService;
	}
	/* this method creates a new task by taking a 
	 * TaskRequest object as input,
	 */
	 public Task createTask(TaskRequest request) {

	        Task task = new Task();

	        task.setTitle(request.getTitle());
	        task.setDescription(request.getDescription());
	        task.setReminderTime(request.getReminderTime());
	        task.setStatus(request.getStatus());
	        
	        Task savedTask = taskRepository.save(task);
	        reminderService.createReminder(savedTask); 
	        // Process the reminder for the newly created task

	        return taskRepository.save(task);
	}
	 
	 /*this method retrieves all tasks from the database
	  *  using the taskRepository's findAll() method and 
	  *  returns them as a list.
	  */
	 public List<Task> getAllTasks() {
	        return taskRepository.findAll();
	 }
	 
	 /* Get a task by its ID. 
	 If the task is not found, it throws a 
	 TaskNotFoundException.
	 */
	 public Task getTaskById(long id) {
		 return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found with id : " + id));
		 
	 }
	 /* This method updates an existing
	  * Task by its ID.
	  */
	 public Task updateTask(long id, TaskRequest request) {
		 
		 Task task = taskRepository.findById(id)
				 .orElseThrow(() -> 
				 new TaskNotFoundException(
						 "Task not found with id : " + id));
		 
		 task.setTitle(request.getTitle());
		 task.setDescription(request.getDescription());
		 task.setReminderTime(request.getReminderTime());
		 task.setStatus(request.getStatus());
		 
		 return taskRepository.save(task);
	 }
	 
	 
	 // This method deletes a task by its ID.
	 public void deleteTask(long id) {
		 Task task = taskRepository.findById(id)
				 .orElseThrow(() -> 
				 new TaskNotFoundException(
						 "Task not found with id : " + id));
		taskRepository.delete(task);					
	 }
}
