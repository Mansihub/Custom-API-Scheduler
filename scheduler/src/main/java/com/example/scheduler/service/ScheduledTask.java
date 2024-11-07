package com.example.scheduler.service;

import com.example.scheduler.model.ExecutionLog;
import com.example.scheduler.model.ScheduledApi;
import com.example.scheduler.repository.ExecutionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    private ScheduledApiService apiService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExecutionLogRepository logRepository;

    // Executes every minute to check for eligible APIs to be triggered
    @Scheduled(fixedRate = 60000) // The global check rate
    public void executeScheduledApis() {
        List<ScheduledApi> apis = apiService.getAllApis();
        for (ScheduledApi api : apis) {
            executeApi(api); 
        }
    }

    // Executes the given API and logs the result
    private void executeApi(ScheduledApi api) {
        ExecutionLog log = new ExecutionLog();
        log.setScheduledApiId(api.getId());
        log.setExecutionTime(LocalDateTime.now());
    
        try {
            // Check if the API needs to be executed
            LocalDateTime lastExecutionTime = api.getLastExecutionTime();
            long intervalMillis = api.getScheduleInterval();
            LocalDateTime nextExecutionTime = lastExecutionTime.plus(intervalMillis, ChronoUnit.MILLIS);

            if (LocalDateTime.now().isAfter(nextExecutionTime)) {
                // Execute the API if it's time based on scheduleInterval
                ResponseEntity<String> response = restTemplate.exchange(
                    api.getUrl(),
                    HttpMethod.valueOf(api.getHttpMethod()),
                    null,
                    String.class
                );
    
                log.setSuccess(true);
                log.setResponse(response.getBody());
    
                // Get the current time for both lastExecutionTime and nextExecutionTime
                LocalDateTime now = LocalDateTime.now();
    
                // Update the API's last execution time after successful execution
                api.setLastExecutionTime(now); // Set last execution time to now
    
                // Calculate and set the next execution time
                LocalDateTime nextExecution = now.plus(intervalMillis, ChronoUnit.MILLIS);
                api.setNextExecutionTime(nextExecution); // Set nextExecutionTime explicitly
    
                // Save the API with both lastExecutionTime and nextExecutionTime
                apiService.saveApi(api);
            }

        } catch (Exception e) {
            log.setSuccess(false);
            log.setResponse("Error: " + e.getMessage());
        } finally {
            logRepository.save(log); // Save log entry to the database
        }
    }
}

