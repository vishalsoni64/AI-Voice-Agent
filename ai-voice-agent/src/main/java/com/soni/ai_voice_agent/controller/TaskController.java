package com.soni.ai_voice_agent.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soni.ai_voice_agent.dto.TaskRequest;
import com.soni.ai_voice_agent.entity.Task;
import com.soni.ai_voice_agent.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")

public class TaskController {
	
	private final TaskService taskService;
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@PostMapping
	public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request) {
		Task task = taskService.createTask(request);
		return new ResponseEntity<>(task, HttpStatus.CREATED);
	}
	@GetMapping
	public ResponseEntity<List<Task>> getAllTasks() {
		List<Task> tasks = taskService.getAllTasks();
		return ResponseEntity.ok(tasks);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable long id){
		Task task = taskService.getTaskById(id);
		return ResponseEntity.ok(task);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(
			@PathVariable long id,
			@Valid @RequestBody TaskRequest request){
		Task updatedTask = taskService.updateTask(id, request);
		
		return ResponseEntity.ok(updatedTask);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable long id) {

	    taskService.deleteTask(id);

	    return ResponseEntity.ok("Task with id " + id + " deleted successfully.");
	}
	}

