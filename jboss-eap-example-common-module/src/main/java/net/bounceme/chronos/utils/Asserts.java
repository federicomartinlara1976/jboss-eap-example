package net.bounceme.chronos.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Asserts {
	
	public static void assertNotNull(Object o) {
		if (o == null) {
			throw new AssertException();
		}
	}
	
	public static void assertNotNull(Object o, String message) {
		if (o == null) {
			throw new AssertException(message);
		}
	}
	
	public static void assertNotNull(Object o, String message, String code) {
		if (o == null) {
			throw new AssertException(message, code);
		}
	}
	
	public static void assertTrue(boolean expression) {
		if (!expression) {
			throw new AssertException();
		}
	}

	public static void assertTrue(boolean expression, String message) {
		if (!expression) {
			throw new AssertException(message);
		}
	}
	
	public static void assertTrue(boolean expression, String message, String code) {
		if (!expression) {
			throw new AssertException(message, code);
		}
	}
	
	public static void assertFalse(boolean expression) {
		if (expression) {
			throw new AssertException();
		}
	}
	
	public static void assertFalse(boolean expression, String message) {
		if (expression) {
			throw new AssertException(message);
		}
	}
	
	public static void assertFalse(boolean expression, String message, String code) {
		if (expression) {
			throw new AssertException(message, code);
		}
	}
}

