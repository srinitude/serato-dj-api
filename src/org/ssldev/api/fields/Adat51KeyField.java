package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat51KeyField extends Field <Adat51KeyField>{

	public static final String ID = "51";

	public Adat51KeyField() {
		super("Key",51);
		consume = new StringConsumeStrategy();
	}

}
