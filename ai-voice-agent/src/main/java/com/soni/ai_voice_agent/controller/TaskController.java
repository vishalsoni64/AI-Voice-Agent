package com.soni.ai_voice_agent.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

}
