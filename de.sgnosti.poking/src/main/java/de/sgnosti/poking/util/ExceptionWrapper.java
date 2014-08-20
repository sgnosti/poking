package de.sgnosti.poking.util;

import java.util.concurrent.Callable;

public class ExceptionWrapper {

	/**
	 * Catch exceptions to throw runtime errors instead
	 * 
	 * @param callable
	 *            what to run
	 * @return the return value of the callable
	 */
	public static <T> T wrap(Callable<T> callable) {
		try {
			return callable.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Catch exceptions to throw runtime errors instead
	 * 
	 * @param block
	 *            what to run (no result expected)
	 */
	public static void wrap(Block block) {
		try {
			block.accept();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@FunctionalInterface
	public interface Block {
		public void accept() throws Exception;
	}
}
