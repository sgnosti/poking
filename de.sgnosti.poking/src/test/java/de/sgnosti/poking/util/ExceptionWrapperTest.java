package de.sgnosti.poking.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExceptionWrapperTest {

	@Test(expected = RuntimeException.class)
	public void testExceptionNotThrown() {
		ExceptionWrapper.wrap(() -> {
			throw new Exception();
		});
	}

	@Test
	public void testReturnValue() throws Exception {
		int result = ExceptionWrapper.wrap(() -> {
			return 3;
		});
		assertEquals(3, result);
	}

	@Test
	public void testVoid() throws Exception {
		ExceptionWrapper.wrap(() -> {
			doNothing();
		});
	}

	@Test(expected = RuntimeException.class)
	public void testVoidThrowingException() throws Exception {
		ExceptionWrapper.wrap(() -> {
			throwException();
		});
	}

	public void doNothing() {

	}

	public void throwException() throws Exception {
		throw new Exception();
	}
}
