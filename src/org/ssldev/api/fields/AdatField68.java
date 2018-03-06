package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.RawStringConsumeStrategy;
/**
 * new field added for Serato DJ Pro.. not sure what its used for 
 */
public class AdatField68 extends Field <AdatField68>{

	public AdatField68() {
		super("Field68", 68);
		consume = new RawStringConsumeStrategy();
	}
	
}
