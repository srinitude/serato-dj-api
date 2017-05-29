package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat4FileNameField extends Field<Adat4FileNameField> {

	public Adat4FileNameField() {
		super("File Name",4);
		consume = new StringConsumeStrategy();
	}

}
