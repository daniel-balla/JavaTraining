/*
 * Copyright (c) 2021 innoWake gmbh Germany. All rights reserved.
 */
package exercise4;

public class WeirdEmployeeApplication {

	private static final Logger log = new Logger();
	
	public void calculateSomeWeirdEmployeeNumbers() {
		log.debug(() -> "Application started.");
		calculateSomeOtherThings();
		log.debug(() -> "Application ended.");
	}

	private void calculateSomeOtherThings() {
		log.debug(() -> "Calculating Some Other Things: " + getValues());
	}

	private String getValues() {
		try {
			/* It is really important that the application thread sleeps for 150 milliseconds here, This can't be changed!!11Q!!!!11! */
			Thread.sleep(150);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
		return "[Values]";
	}

	public void withDebugLogging(boolean withDebugLogging) {
		log.isDebug = withDebugLogging;
	}
}
