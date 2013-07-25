package org.carracoo.beans.lang;

import org.carracoo.beans.Property;

public class LongProperty extends Property<Long> {

	public static class LongOptions extends Options {
	}

	public LongProperty(){
		set(0L);
	}

	public void increment() {
		increment(1L);
	}

	public void increment(Long step) {
		set(value+step);
	}

	public void decrement() {
		decrement(1L);
	}

	public void decrement(Long step) {
		set(value-step);
	}
}
