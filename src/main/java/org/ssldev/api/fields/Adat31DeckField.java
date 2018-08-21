package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.IntConsumeStrategy;

public class Adat31DeckField extends Field <Adat31DeckField>{

	public static final String ID = "31";

	public Adat31DeckField() {
		super("Deck",31);
		consume = new IntConsumeStrategy();
	}

}
