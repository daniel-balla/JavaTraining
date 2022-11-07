/*
 * Copyright (c) 2021 innoWake gmbh Germany. All rights reserved.
 */
package exercise4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * This Test tests the weird Employee Application.
 * It runs the Application with debug logging to see if it runs fine,
 * and then runs the Application again without debug logging to see, if it 
 * runs as fast as is needed ( under 2 seconds).
 */
class TestApplication {

	@Test
	void testApplicationRuns() {
		WeirdEmployeeApplication app = new WeirdEmployeeApplication();
		app.withDebugLogging(true);
		app.calculateSomeWeirdEmployeeNumbers();
	}
	
	@Test
	void testApplicationFasterTwoSeconds() {
		WeirdEmployeeApplication app = new WeirdEmployeeApplication();
		app.withDebugLogging(false);
		
		Timer timer = Timer.start();
		app.calculateSomeWeirdEmployeeNumbers();
		
		long timeRunning = timer.end();
		assertTrue(100 > timeRunning, String.format("Application was to slow, please make it faster. Took %sms, but allowed are only %sms.", timeRunning, 100));
	}
}
