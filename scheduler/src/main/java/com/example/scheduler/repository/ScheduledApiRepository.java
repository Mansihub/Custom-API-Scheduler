package com.example.scheduler.repository;

import com.example.scheduler.model.ScheduledApi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface ScheduledApiRepository extends JpaRepository<ScheduledApi, Long> {
     // Custom query method to find an API by its URL
     Optional<ScheduledApi> findByUrl(String url);
}
