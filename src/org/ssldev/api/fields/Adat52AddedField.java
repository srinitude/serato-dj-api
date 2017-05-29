package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.OneByteConsumeStrategy;

public class Adat52AddedField extends Field<Adat52AddedField> {

	public Adat52AddedField() {
		super("Added",52);
		consume = new OneByteConsumeStrategy();
	}

}
