package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat20Remixer extends Field <Adat20Remixer>{

	public Adat20Remixer() {
		super("Remixer",20);
		consume = new StringConsumeStrategy();
	}

}
