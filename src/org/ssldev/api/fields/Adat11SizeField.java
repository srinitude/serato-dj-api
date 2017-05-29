package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat11SizeField extends Field<Adat11SizeField> {

	public Adat11SizeField() {
		super("Size",11);
		consume = new StringConsumeStrategy();
	}

}
