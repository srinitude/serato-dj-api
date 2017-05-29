package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat14FrequencyField extends Field <Adat14FrequencyField>{

	public Adat14FrequencyField() {
		super("Frequency",14);
		consume = new StringConsumeStrategy();
	}

}
