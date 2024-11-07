package com.example.scheduler.controller;

import com.example.scheduler.model.ExecutionLog;
import com.example.scheduler.model.ScheduledApi;
import com.example.scheduler.repository.ExecutionLogRepository;
import com.example.scheduler.service.ScheduledApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ScheduledApiController {

    @Autowired
    private ScheduledApiService service;

     @Autowired
    private ExecutionLogRepository logRepository; 

    // Add a new API
    @PostMapping("/add")
    public ScheduledApi addApi(@RequestBody ScheduledApi api) {
        System.out.println("Received POST request to add API: " + api.getUrl());
        ScheduledApi savedApi = service.saveApi(api); // Save the API
        return savedApi; 
    }

    // Update an existing API
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateApi(@PathVariable Long id, @RequestBody ScheduledApi api) {
        Optional<ScheduledApi> updatedApi = service.updateApi(id, api);
       
        if (updatedApi.isPresent()) {
            return ResponseEntity.ok(updatedApi.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("API with ID " + id + " not found.");
        }
    }

    // Delete an API
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApi(@PathVariable Long id) {
        boolean isDeleted = service.deleteApi(id);
        if (isDeleted) {
            return ResponseEntity.ok("API deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("API with the given ID not found.");
        }
    }

    @GetMapping("/logs/{apiId}")
public List<ExecutionLog> getLogs(@PathVariable Long apiId) {
    return logRepository.findByScheduledApiId(apiId);
}

}
