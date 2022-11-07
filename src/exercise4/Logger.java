/*
 * Copyright (c) 2021 innoWake gmbh Germany. All rights reserved.
 */
package exercise4;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * Simple (and bad) logger implementation.
 */
public class Logger {

	public boolean isDebug = false;

	/**
	 * Writes the {@code string} to the Console if debug logging is enabled.
	 *
	 * @param string the string to write
	 * @throws IOException
	 */
	public void debug(Supplier<String> msg) {
		if (isDebug) {
			System.out.println(msg.get());
		}
	}
}
