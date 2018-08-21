package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;

public class Adat8AlbumField extends Field <Adat8AlbumField>{

	public Adat8AlbumField() {
		super("Album",8);
		consume = new StringConsumeStrategy();
	}
}
