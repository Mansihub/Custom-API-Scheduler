package com.example.scheduler.repository;

import com.example.scheduler.model.ExecutionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExecutionLogRepository extends JpaRepository<ExecutionLog, Long> {

    // Custom query method to find ExecutionLogs by scheduledApiId
    List<ExecutionLog> findByScheduledApiId(Long scheduledApiId);
}
