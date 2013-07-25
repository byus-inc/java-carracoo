package org.carracoo.beans.lang;

import org.carracoo.beans.Property;

public class IntegerProperty extends Property<Integer> {
	public static class Options extends Property.Options {
	}
	public IntegerProperty(){
		set(0);
	}

	public void increment() {
		increment(1);
	}

	public void increment(Integer step) {
		set(value+step);
	}

	public void decrement() {
		decrement(1);
	}

	public void decrement(Integer step) {
		set(value-step);
	}
}
