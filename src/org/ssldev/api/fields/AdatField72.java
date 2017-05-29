package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.RawStringConsumeStrategy;
/**
 * appears on SSL shutdown.. not sure what it means
 */
public class AdatField72 extends Field<AdatField72>{
	
	public AdatField72() {
		super("Field72",72);
		consume = new RawStringConsumeStrategy();
	}

}
