package de.ars.schulung.tests.garage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class EngineTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS) // methods return mocks instead of null
	private Logger logger;

	@InjectMocks
	private Engine engine;

	@Test
	@DisplayName("test start - usage of logger")
	void testStartLoggerUsage() {
		try {
			engine.start();
			verify(logger).info(any(String.class));
		} finally {
			engine.stop(); // stop background thread
		}
	}

}
