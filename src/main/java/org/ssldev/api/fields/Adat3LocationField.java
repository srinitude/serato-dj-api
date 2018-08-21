package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat3LocationField extends Field<Adat3LocationField> {

	public Adat3LocationField() {
		super("Location",3);
		consume = new StringConsumeStrategy();
	}

}
