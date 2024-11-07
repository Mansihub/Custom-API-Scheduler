package com.example.scheduler.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class ScheduledApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String httpMethod;
    private Long scheduleInterval; // In milliseconds
    private LocalDateTime lastExecutionTime; 
    private LocalDateTime nextExecutionTime; 

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Long getScheduleInterval() {
        return scheduleInterval;
    }

    public void setScheduleInterval(Long scheduleInterval) {
        this.scheduleInterval = scheduleInterval;
    }

    public LocalDateTime getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(LocalDateTime lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }

    public LocalDateTime getNextExecutionTime() {
        return nextExecutionTime;
    }

    public void setNextExecutionTime(LocalDateTime nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
    }

    /**
     * Returns the next execution time based on the scheduleInterval and lastExecutionTime.
     * If the lastExecutionTime is null, it returns the current time (first execution).
     * @return Next execution time
     */
    public LocalDateTime calculateNextExecutionTime() {
        // If lastExecutionTime is null (i.e., first execution), return the current time
        if (this.lastExecutionTime == null) {
            return LocalDateTime.now();
        }

        // Ensure scheduleInterval is set correctly; if not, return the current time
        if (this.scheduleInterval == null || this.scheduleInterval <= 0) {
            return LocalDateTime.now();
        }

        // Add the scheduleInterval (in milliseconds) to the last execution time
        return lastExecutionTime.plus(scheduleInterval, ChronoUnit.MILLIS);
    }
}
