package com.soni.ai_voice_agent.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.soni.ai_voice_agent.entity.Task;
import com.soni.ai_voice_agent.exception.TaskNotFoundException;
import com.soni.ai_voice_agent.reminder.service.ReminderService;
import com.soni.ai_voice_agent.repository.TaskRepository;

public class TaskServiceTest {
	@Mock
	private TaskRepository taskRepository;
	@Mock
	private ReminderService reminderService;
	@InjectMocks
	private TaskService taskService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	@Test
	void shouldReturnTaskWhenIdExists() {
		Task task = new Task();
		
		task.setId(1L);
		task.setTitle("Learning Spring Boot");
		
		when(taskRepository.findById(1L))
				.thenReturn(java.util.Optional.of(task));
		
		Task result = taskService.getTaskById(1L);
		assertNotNull(result);
	    assertEquals(1L, result.getId());
	    assertEquals("Learning Spring Boot", result.getTitle());

	    verify(taskRepository).findById(1L);
		
	}
	
	@Test
	void shouldThrowExceptionWhenTaskDoesNotExist() {

	    when(taskRepository.findById(99L))
	            .thenReturn(java.util.Optional.empty());

	    assertThrows(
	            TaskNotFoundException.class,
	            () -> taskService.getTaskById(99L)
	    );

	    verify(taskRepository).findById(99L);
	}

}
