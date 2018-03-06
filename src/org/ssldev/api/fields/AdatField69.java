package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.RawStringConsumeStrategy;
/**
 * new field added for Serato DJ Pro.. not sure what its used for 
 */
public class AdatField69 extends Field <AdatField69>{

	public AdatField69() {
		super("Field69", 69);
		consume = new RawStringConsumeStrategy();
	}
	
}
