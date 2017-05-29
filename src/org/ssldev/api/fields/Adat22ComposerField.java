package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat22ComposerField extends Field <Adat22ComposerField>{

	public Adat22ComposerField() {
		super("Composer",22);
		consume = new StringConsumeStrategy();
	}

}
