package com.soni.ai_voice_agent.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.soni.ai_voice_agent.entity.Task;
import com.soni.ai_voice_agent.service.TaskService;


public class TaskControllerTest {
	
	    private MockMvc mockMvc;

	    @Mock
	    private TaskService taskService;

	    @InjectMocks
	    private TaskController taskController;

	    @BeforeEach
	    void setUp() {

	        MockitoAnnotations.openMocks(this);

	        mockMvc = MockMvcBuilders
	                .standaloneSetup(taskController)
	                .build();
	    }
	    
	    @Test
	    void shouldGetAllTasks() throws Exception {

	        Task task = new Task();

	        task.setId(1L);
	        task.setTitle("Learn Spring Boot");
	        task.setDescription("Study Spring Boot");
	        task.setReminderTime(
	                LocalDateTime.of(2026, 8, 15, 10, 0)
	        );

	        when(taskService.getAllTasks())
	                .thenReturn(List.of(task));

	        mockMvc.perform(
	                get("/api/tasks")
	        )
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$[0].id").value(1))
	        .andExpect(jsonPath("$[0].title")
	                .value("Learn Spring Boot"));

	        verify(taskService).getAllTasks();
	    }
	    
	    
	    @Test
	    void shouldGetTaskById() throws Exception {

	        Task task = new Task();

	        task.setId(1L);
	        task.setTitle("Learn Spring Boot");
	        task.setDescription("Study Spring Boot");

	        when(taskService.getTaskById(1L))
	                .thenReturn(task);

	        mockMvc.perform(
	                get("/api/tasks/1")
	        )
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(1))
	        .andExpect(jsonPath("$.title")
	                .value("Learn Spring Boot"));

	        verify(taskService).getTaskById(1L);
	    }
	    
	    
	    @Test
	    void shouldDeleteTask() throws Exception {

	        doNothing()
	                .when(taskService)
	                .deleteTask(1L);

	        mockMvc.perform(
	                delete("/api/tasks/1")
	        )
	        .andExpect(status().isOk())
	        .andExpect(
	                content().string(
	                        "Task with id 1 deleted successfully."
	                )
	        );

	        verify(taskService).deleteTask(1L);
	    }
	    
	    
	    
	    @Test
	    void shouldCreateTask() throws Exception {

	        Task task = new Task();

	        task.setId(10L);
	        task.setTitle("AI Voice Agent");
	        task.setDescription("Build reminder system");
	        task.setReminderTime(
	                LocalDateTime.of(2026, 8, 15, 10, 0)
	        );

	        when(taskService.createTask(any()))
	                .thenReturn(task);

	        String requestBody = """
	                {
	                    "title": "AI Voice Agent",
	                    "description": "Build reminder system",
	                    "reminderTime": "2026-08-15T10:00:00",
	                    "status": "PENDING"
	                }
	                """;

	        mockMvc.perform(
	                post("/api/tasks")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(requestBody)
	        )
	        .andExpect(status().isCreated())
	        .andExpect(jsonPath("$.id").value(10))
	        .andExpect(jsonPath("$.title")
	                .value("AI Voice Agent"));

	        verify(taskService).createTask(any());
	    }
	    
	    
	    
	    @Test
	    void shouldUpdateTask() throws Exception {

	        Task task = new Task();

	        task.setId(1L);
	        task.setTitle("Updated Task");
	        task.setDescription("Updated description");
	        task.setReminderTime(
	                LocalDateTime.of(2026, 8, 16, 12, 0)
	        );

	        when(taskService.updateTask(eq(1L), any()))
	                .thenReturn(task);

	        String requestBody = """
	                {
	                    "title": "Updated Task",
	                    "description": "Updated description",
	                    "reminderTime": "2026-08-16T12:00:00",
	                    "status": "PENDING"
	                }
	                """;

	        mockMvc.perform(
	                put("/api/tasks/1")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(requestBody)
	        )
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.id").value(1))
	        .andExpect(jsonPath("$.title")
	                .value("Updated Task"));

	        verify(taskService)
	                .updateTask(eq(1L), any());
	    }
	}


