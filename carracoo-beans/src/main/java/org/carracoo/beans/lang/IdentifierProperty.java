package org.carracoo.beans.lang;

import org.carracoo.beans.Property;

public abstract class IdentifierProperty<V> extends Property<V> {
	public static class Options extends Property.Options {
	}

	abstract public void generate();
}
