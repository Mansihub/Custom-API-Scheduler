package com.example.scheduler.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Log {

    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private String message;

    @Column(name = "scheduled_api_id")  
    private Long scheduledApiId; 

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getScheduledApiId() {
        return scheduledApiId;
    }

    public void setScheduledApiId(Long scheduledApiId) {
        this.scheduledApiId = scheduledApiId;
    }
}
