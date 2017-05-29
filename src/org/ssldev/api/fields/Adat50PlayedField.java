package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.OneByteConsumeStrategy;

public class Adat50PlayedField extends Field <Adat50PlayedField>{
	public static final String ID = "50";

	public Adat50PlayedField() {
		super("Played",50);
		consume = new OneByteConsumeStrategy();
	}
}
