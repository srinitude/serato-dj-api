package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.IntConsumeStrategy;

public class Adat48SessiondField extends Field <Adat48SessiondField>{

	public Adat48SessiondField() {
		super("Session ID",48);
		consume = new IntConsumeStrategy();
	}

}
