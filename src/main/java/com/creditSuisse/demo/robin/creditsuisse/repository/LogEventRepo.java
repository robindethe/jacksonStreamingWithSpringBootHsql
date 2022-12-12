package com.creditSuisse.demo.robin.creditsuisse.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.creditSuisse.demo.robin.creditsuisse.model.LogEvent;

@Repository
public interface LogEventRepo extends JpaRepository<LogEvent,String>{
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<LogEvent> findById(String id);
}
