

package com.example.scheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.scheduler.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
}
