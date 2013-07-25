package org.carracoo.beans.lang;

import org.carracoo.beans.Property;

public class FloatProperty extends Property<Float> {
	public static class Options extends Property.Options {
	}

	public FloatProperty(){
		set(0f);
	}

	public void increment() {
		increment(1f);
	}

	public void increment(Float step) {
		set(value+step);
	}

	public void decrement() {
		decrement(1f);
	}

	public void decrement(Float step) {
		set(value-step);
	}
}
