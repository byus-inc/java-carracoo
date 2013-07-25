package org.carracoo.beans.lang;

import org.carracoo.beans.Property;

public class DoubleProperty extends Property<Double> {
	public static class Options extends Property.Options {
	}

	public DoubleProperty(){
		set(0.0);
	}

	public void increment() {
		increment(1.0);
	}

	public void increment(Double step) {
		set(value+step);
	}

	public void decrement() {
		decrement(1.0);
	}

	public void decrement(Double step) {
		set(value-step);
	}
}
