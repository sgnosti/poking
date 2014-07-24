package de.sgnosti.poking.util;

import java.util.concurrent.Callable;

public class ExceptionWrapper {

	/**
	 * TODO wrap exceptions, is this how you do it?
	 * Catch exceptions to throw runtime errors instead
	 * @param callable what to run
	 * @return the return value of the callable
	 */
	public static <T> T wrap (Callable<T> callable) {
		try {
			return callable.call();
		}
		catch (Exception e) {
			throw new RuntimeException (e);
		}
	}
	
	public static void wrap(Runnable runnable) {
		try {
			runnable.run();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
