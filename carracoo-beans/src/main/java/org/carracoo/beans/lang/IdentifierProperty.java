package org.carracoo.beans.lang;

public abstract class IdentifierProperty<V> extends ValueProperty<V> {
	public static class Options extends ValueProperty.Options {
	}

	abstract public void generate();
}
