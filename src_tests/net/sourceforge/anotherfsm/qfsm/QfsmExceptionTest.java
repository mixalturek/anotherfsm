package net.sourceforge.anotherfsm.qfsm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class QfsmExceptionTest {
	@Test
	public void testQfsmException() {
		QfsmException exception = new QfsmException();
		assertNull(exception.getMessage());
	}

	@Test
	public void testQfsmExceptionString() {
		QfsmException exception = new QfsmException("test");
		assertEquals("test", exception.getMessage());
	}

	@Test
	public void testQfsmExceptionThrowable() {
		QfsmException exception = new QfsmException(new NullPointerException());
		assertEquals(new NullPointerException().getClass(), exception
				.getCause().getClass());
	}

	@Test
	public void testQfsmExceptionStringThrowable() {
		QfsmException exception = new QfsmException("test",
				new NullPointerException());
		assertEquals("test", exception.getMessage());
		assertEquals(new NullPointerException().getClass(), exception
				.getCause().getClass());
	}
}
