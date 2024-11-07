package com.example.scheduler.service;

import com.example.scheduler.model.ExecutionLog;
import com.example.scheduler.model.ScheduledApi;
import com.example.scheduler.repository.ExecutionLogRepository;
import com.example.scheduler.repository.ScheduledApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduledApiService {

    @Autowired
    private ScheduledApiRepository apiRepository;

    @Autowired
    private LogService logService;

   

  // Fetch all scheduled APIs with caching
    @Cacheable("scheduledApis")  // Caches the list of all APIs
    public List<ScheduledApi> getAllApis() {
        return apiRepository.findAll();
    }

    // Save a new Scheduled API and update cache
    @CacheEvict(value = "scheduledApis", allEntries = true)  // Clears cached list after adding a new API
      public ScheduledApi saveApi(ScheduledApi api) {
        Optional<ScheduledApi> existingApiOpt = apiRepository.findByUrl(api.getUrl());
        ScheduledApi savedApi;
    
        if (existingApiOpt.isPresent()) {
            ScheduledApi existingApi = existingApiOpt.get();
            System.out.println("API already exists, updating times for URL: " + api.getUrl());
    
            // Update `lastExecutionTime` and `nextExecutionTime` for the existing API
            if (api.getLastExecutionTime() != null) {
                existingApi.setLastExecutionTime(api.getLastExecutionTime());
            }
    
            if (api.getNextExecutionTime() != null) {
                existingApi.setNextExecutionTime(api.getNextExecutionTime());
            }
    
            // Save and get the updated existing API
            savedApi = apiRepository.save(existingApi);
            
            // Log the update action
            logService.createLog("API Updated", "Updated existing API with URL: " + api.getUrl(), savedApi.getId());
            
        } else {
            // If API doesn't exist, save as a new entry
            if (api.getLastExecutionTime() == null) {
                System.out.println("Setting lastExecutionTime to now for API: " + api.getUrl());
                api.setLastExecutionTime(LocalDateTime.now());  // Initialize lastExecutionTime to current time
            }
    
            if (api.getNextExecutionTime() == null) {
                LocalDateTime nextExecution = api.calculateNextExecutionTime();
                api.setNextExecutionTime(nextExecution);
            }
    
            System.out.println("Saving new API: " + api.getUrl());  
            savedApi = apiRepository.save(api);  // Save API to database
    
            // Log the addition of a new API
            logService.createLog("API Added", "Added new API with URL: " + api.getUrl(), savedApi.getId());
        }
    
        return savedApi;
    }
    
    // Update an existing Scheduled API and update cache
    @CacheEvict(value = "scheduledApis", allEntries = true)  // Clears cached list after updating an API
    public Optional<ScheduledApi> updateApi(Long id, ScheduledApi api) {
        if (apiRepository.existsById(id)) {
            api.setId(id);
            logService.createLog("API Updated", "Updated API with ID: " + id,id);
            return Optional.of(apiRepository.save(api)); // Return Optional of updated API
        }
        return Optional.empty(); // Return empty if API doesn't exist
    }

    // Delete a Scheduled API and update cache
    @CacheEvict(value = "scheduledApis", allEntries = true)  // Clears cached list after deleting an API
    public boolean deleteApi(Long id) {
        if (apiRepository.existsById(id)) {
            apiRepository.deleteById(id);
            logService.createLog("API Deleted", "Deleted API with ID: " + id,id);
            return true; // Deletion successful
        }
        return false; // API with given ID doesn't exist
    }
}
