package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;
/**
 * 1: id 0002
 * 2: length
 * 3: val to length
 */
public class Adat2FullPathField extends Field <Adat2FullPathField>{
	public static String ID = "2";
	
	public Adat2FullPathField() {
		super("Full Path",2);
		consume = new StringConsumeStrategy();
	}

}
