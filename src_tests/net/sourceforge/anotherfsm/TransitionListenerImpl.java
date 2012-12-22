package net.sourceforge.anotherfsm;

import net.sourceforge.anotherfsm.Event;
import net.sourceforge.anotherfsm.State;
import net.sourceforge.anotherfsm.TransitionListener;

public class TransitionListenerImpl implements TransitionListener {
	public int transitionsNum = 0;

	@Override
	public void onTransition(State source, Event event, State destination) {
		++transitionsNum;
	}
}
