package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.RawStringConsumeStrategy;
/**
 * appears on SSL shutdown.. not sure what it means
 */
public class AdatField70 extends Field<AdatField70>{
	
	public AdatField70() {
		super("Field70",70);
		consume = new RawStringConsumeStrategy();
	}

}
