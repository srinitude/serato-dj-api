package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.RawStringConsumeStrategy;
/**
 * new field added for Serato DJ Pro.. not sure what its used for 
 */
public class AdatField63 extends Field <AdatField63>{

	public AdatField63() {
		super("Field63", 63);
		consume = new RawStringConsumeStrategy();
	}
	
}
