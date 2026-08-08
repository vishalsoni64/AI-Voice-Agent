package com.soni.ai_voice_agent.repository;

import org.springframework.stereotype.Repository;
import com.soni.ai_voice_agent.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>  {	
	

}
