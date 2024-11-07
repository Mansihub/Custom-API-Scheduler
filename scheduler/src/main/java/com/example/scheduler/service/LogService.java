package com.example.scheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.scheduler.repository.LogRepository;
import com.example.scheduler.model.Log;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

   
    public void createLog(String action, String message, Long scheduledApiId) {
        Log log = new Log();
        log.setAction(action);
        log.setMessage(message);  
        log.setScheduledApiId(scheduledApiId);  // Save the scheduledApiId to the log

        logRepository.save(log);  // Save the log entry to the database
    }
}
