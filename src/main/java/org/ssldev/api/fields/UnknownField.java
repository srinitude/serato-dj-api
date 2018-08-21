package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.RawStringConsumeStrategy;
/**
 * unknown field assumes:
 * 1. length
 * 2. string of size 'length'
 */
public class UnknownField extends Field<UnknownField>{

	public UnknownField() {
		super("UnknownField", -1);
		consume = new RawStringConsumeStrategy();
	}
}
