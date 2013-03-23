package net.sourceforge.anotherfsm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StateAdapterTest {
	@Test
	public void testStateAdapter() {
		StateAdapter adapter = new StateAdapter();
		assertEquals(StateListener.Type.LOOP_PROCESS, adapter.getType());
	}

	@Test
	public void testStateAdapterType() {
		StateAdapter adapter = new StateAdapter(StateListener.Type.LOOP_PROCESS);
		assertEquals(StateListener.Type.LOOP_PROCESS, adapter.getType());

		adapter = new StateAdapter(StateListener.Type.LOOP_NO_PROCESS);
		assertEquals(StateListener.Type.LOOP_NO_PROCESS, adapter.getType());
	}

	@Test
	public void testOnStateEnter() {
		StateAdapter adapter = new StateAdapter();
		adapter.onStateEnter(new State("state"), new TypeEventA(), new State(
				"state"));
	}

	@Test
	public void testOnStateExit() {
		StateAdapter adapter = new StateAdapter();
		adapter.onStateExit(new State("state"), new TypeEventA(), new State(
				"state"));
	}
}
