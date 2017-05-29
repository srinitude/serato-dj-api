package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat9GenreField extends Field <Adat9GenreField>{

	public Adat9GenreField() {
		super("Genre",9);
		consume = new StringConsumeStrategy();
	}

}
