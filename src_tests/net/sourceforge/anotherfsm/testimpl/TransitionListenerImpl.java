package net.sourceforge.anotherfsm.testimpl;

import net.sourceforge.anotherfsm.api.Event;
import net.sourceforge.anotherfsm.api.State;
import net.sourceforge.anotherfsm.api.TransitionListener;

public class TransitionListenerImpl implements TransitionListener {
	public int transitionsNum = 0;

	@Override
	public void onTransition(State source, Event event, State destination) {
		++transitionsNum;
	}
}
