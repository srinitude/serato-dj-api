package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.IntConsumeStrategy;

public class Adat15BpmField extends Field <Adat15BpmField>{

	public static final String ID = "15";

	public Adat15BpmField() {
		super("BPM",15);
		consume = new IntConsumeStrategy();
	}

}
