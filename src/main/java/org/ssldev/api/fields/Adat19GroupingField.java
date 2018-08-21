package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat19GroupingField extends Field <Adat19GroupingField>{

	public Adat19GroupingField() {
		super("Grouping",19);
		consume = new StringConsumeStrategy();
	}

}
