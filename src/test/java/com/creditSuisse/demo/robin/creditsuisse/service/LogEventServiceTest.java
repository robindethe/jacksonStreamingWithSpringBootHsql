package com.creditSuisse.demo.robin.creditsuisse.service;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.creditSuisse.demo.robin.creditsuisse.model.LogEvent;
import com.creditSuisse.demo.robin.creditsuisse.repository.LogEventRepo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LogEventServiceTest {
	
	@Mock
	LogEventRepo repo;
	
	@InjectMocks
	LogEventService service;
	
	@Test
	public void test_process() {
		
		LogEvent logEvent = new LogEvent();
		logEvent.setAlert(false);
		logEvent.setDuration(0);
		logEvent.setHost("1");
		logEvent.setId("1");
		logEvent.setState("A");
		logEvent.setTimestamp(2L);
		logEvent.setType("t2");
		
		LogEvent persisitedLogEvent = new LogEvent();
		persisitedLogEvent.setAlert(false);
		persisitedLogEvent.setDuration(0);
		persisitedLogEvent.setHost("1");
		persisitedLogEvent.setId("1");
		persisitedLogEvent.setState("A");
		persisitedLogEvent.setTimestamp(1L);
		persisitedLogEvent.setType("t1");
		
		when(repo.findById("1")).thenReturn(Optional.of(persisitedLogEvent));
		service.process(logEvent);
		verify(repo, times(1)).findById("1");
	}
	
	@Test
	public void test_process_whenFinishedEventCameBeforeStarted() {
				
		LogEvent logEvent = new LogEvent();
		logEvent.setAlert(false);
		logEvent.setDuration(0);
		logEvent.setHost("1");
		logEvent.setId("1");
		logEvent.setState("A");
		logEvent.setTimestamp(1L);
		logEvent.setType("t1");
		
		LogEvent persisitedLogEvent = new LogEvent();
		persisitedLogEvent.setAlert(false);
		persisitedLogEvent.setDuration(0);
		persisitedLogEvent.setHost("1");
		persisitedLogEvent.setId("1");
		persisitedLogEvent.setState("A");
		persisitedLogEvent.setTimestamp(3L);
		persisitedLogEvent.setType("t3");
		
		when(repo.findById("1")).thenReturn(Optional.of(persisitedLogEvent));
		service.process(logEvent);
		verify(repo, times(1)).findById("1");
	}
	
	@Test
	public void test_process_whenNoDataExistInDb() {
		LogEvent logEvent = new LogEvent();
		logEvent.setAlert(false);
		logEvent.setDuration(0);
		logEvent.setHost("1");
		logEvent.setId("1");
		logEvent.setState("A");
		logEvent.setTimestamp(2L);
		logEvent.setType("t2");
		
		LogEvent persisitedLogEvent = new LogEvent();
		persisitedLogEvent.setAlert(false);
		persisitedLogEvent.setDuration(0);
		persisitedLogEvent.setHost("1");
		persisitedLogEvent.setId("1");
		persisitedLogEvent.setState("A");
		persisitedLogEvent.setTimestamp(1L);
		persisitedLogEvent.setType("t1");
		
		when(repo.findById("1")).thenReturn(Optional.empty());
		service.process(logEvent);
		verify(repo, times(1)).findById("1");
	}
	
	
}
