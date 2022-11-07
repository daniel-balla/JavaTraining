/*
 * Copyright (c) 2021 innoWake gmbh Germany. All rights reserved.
 */
package exercise4;

public class Timer {

	private final long start;

	public Timer(final long start) {
		this.start = start;
	}

	public static Timer start() {
		return new Timer(System.currentTimeMillis());
	}

	public long end() {
		final long end = System.currentTimeMillis();
		return end - start;
	}
}
