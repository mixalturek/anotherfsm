package net.sourceforge.anotherfsm;

public class NonstandardTimeoutEvent implements TimeoutEvent {
	@Override
	public long getTimeout() {
		return 7656;
	}

	@Override
	public TimeoutEvent.Type getType() {
		return TimeoutEvent.Type.LOOP_RESTART;
	}

	@Override
	public int hashCode() {
		return HASH_CODE;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		return obj instanceof TimeoutEvent;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
