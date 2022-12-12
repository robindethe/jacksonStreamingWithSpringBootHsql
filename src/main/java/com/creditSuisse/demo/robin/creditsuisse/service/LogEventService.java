package com.creditSuisse.demo.robin.creditsuisse.service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.creditSuisse.demo.robin.creditsuisse.model.LogEvent;
import com.creditSuisse.demo.robin.creditsuisse.repository.LogEventRepo;

@Service
public class LogEventService {
	private static final Logger LOG = LoggerFactory.getLogger(LogEventService.class);

	@Autowired
	LogEventRepo logEventRepo;

	@Async
	@Transactional
	public void process(LogEvent logEvent) {
		Optional<LogEvent> persistedLogEvent = logEventRepo.findById(logEvent.getId());
		persistedLogEvent.map(e -> {
			long startTimeStamp = e.getTimestamp();
			long endTimeStamp = logEvent.getTimestamp();
			LocalDateTime startLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTimeStamp),
					TimeZone.getDefault().toZoneId());
			LocalDateTime endLocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTimeStamp),
					TimeZone.getDefault().toZoneId());

			long duration = Duration.between(startLocalDateTime, endLocalDateTime).toMillis();
			if(duration <  0) {
				logEvent.setState(e.getState());
				duration = -(duration);
			}
			logEvent.setDuration(duration);
			logEvent.setAlert(duration > 4 ? true : false);
			return logEventRepo.saveAndFlush(logEvent);
		}).orElseGet(() -> logEventRepo.save(logEvent));
		

	}

}
