package com.creditSuisse.demo.robin.creditsuisse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.creditSuisse.demo.robin.creditsuisse.service.LogEventService;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LogEventCommandLineRunnerTest {
	@Mock
	LogEventService service;
	
	@InjectMocks
	LogEventCommandLineRunner runner;
	
	@Test
	public void test() throws Exception {
		runner.run("abc");
		verify(service,atLeastOnce()).process(any());
	}
}
