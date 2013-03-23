package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class FsmExceptionTest {
	@Test
	public void testFsmException() {
		FsmException exception = new FsmException();
		assertNull(exception.getMessage());
	}

	@Test
	public void testFsmExceptionString() {
		FsmException exception = new FsmException("test");
		assertEquals("test", exception.getMessage());
	}

	@Test
	public void testFsmExceptionThrowable() {
		FsmException exception = new FsmException(new NullPointerException());
		assertEquals(new NullPointerException().getClass(), exception
				.getCause().getClass());
	}

	@Test
	public void testFsmExceptionStringThrowable() {
		FsmException exception = new FsmException("test",
				new NullPointerException());
		assertEquals("test", exception.getMessage());
		assertEquals(new NullPointerException().getClass(), exception
				.getCause().getClass());
	}

}
