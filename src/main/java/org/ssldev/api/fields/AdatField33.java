package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.RawStringConsumeStrategy;

public class AdatField33 extends Field <AdatField33>{

	public AdatField33() {
		super("Field33",33);
		consume = new RawStringConsumeStrategy();
	}

}
