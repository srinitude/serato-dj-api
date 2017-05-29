package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat18LanguageField extends Field <Adat18LanguageField>{

	public Adat18LanguageField() {
		super("Language",18);
		consume = new StringConsumeStrategy();
	}

}
