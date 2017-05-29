package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat10LengthField extends Field<Adat10LengthField> {

	public Adat10LengthField() {
		super("Length",10);
		consume = new StringConsumeStrategy();
	}

}
