package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat21LabelField extends Field<Adat21LabelField> {
	public Adat21LabelField() {
		super("Label",21);
		consume = new StringConsumeStrategy();
	}

}
