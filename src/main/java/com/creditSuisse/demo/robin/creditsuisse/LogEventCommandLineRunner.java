package com.creditSuisse.demo.robin.creditsuisse;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.creditSuisse.demo.robin.creditsuisse.model.LogEvent;
import com.creditSuisse.demo.robin.creditsuisse.service.LogEventService;

import antlr.Token;

@Component
public class LogEventCommandLineRunner implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(LogEventCommandLineRunner.class);
	
	@Autowired
	LogEventService logEventService;

	@Override
	public void run(String... args) throws Exception {
		JsonParser parser = null;
		try {
			JsonFactory jsonfactory = new JsonFactory();
			File source = ResourceUtils.getFile("classpath:logfile.txt");
			parser = jsonfactory.createJsonParser(source);
			parser.setCodec(new ObjectMapper());
			parser.nextToken();
			
			
			while (parser.hasCurrentToken()) {
				LogEvent logEvent = parser.readValueAs(LogEvent.class);
				parser.nextToken();
				LOG.info(">>>>>>>>>>>>>>>>"+logEvent.getId());
				logEventService.process(logEvent);
			}
			
			
		} catch (JsonGenerationException jge) {
			jge.printStackTrace();
		} catch (JsonMappingException jme) {
			jme.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} finally {
			if (null != parser) {
				parser.close();
			}
			
		}

		
	}
	
	
}
