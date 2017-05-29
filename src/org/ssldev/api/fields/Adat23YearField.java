package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat23YearField extends Field <Adat23YearField>{

	public Adat23YearField() {
		super("Year",23);
		consume = new StringConsumeStrategy();
	}

}
