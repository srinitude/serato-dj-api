package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat6TitleField extends Field <Adat6TitleField>{

	public static final String ID = "6";

	public Adat6TitleField() {
		super("Title",6);
		consume = new StringConsumeStrategy();
	}
}
