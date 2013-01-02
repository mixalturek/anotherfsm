package net.sourceforge.anotherfsm;

public class NonstandardOtherEvent implements OtherEvent {
	@Override
	public Event getSourceEvent() {
		return NullEvent.instance;
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

		return obj instanceof OtherEvent;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
