package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat13BitrateField extends Field<Adat13BitrateField> {

	public Adat13BitrateField() {
		super("bitrate",13);
		consume = new StringConsumeStrategy();
	}
}
