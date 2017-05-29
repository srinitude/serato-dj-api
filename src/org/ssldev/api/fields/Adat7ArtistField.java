package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat7ArtistField extends Field <Adat7ArtistField>{

	public static final String ID = "7";

	public Adat7ArtistField() {
		super("Artist",7);
		consume = new StringConsumeStrategy();
	}

}
