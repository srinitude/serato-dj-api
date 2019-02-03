package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.StringConsumeStrategy;
/**
 * new field added for Serato DJ Pro.  May represent player type (e.g. offline player) 
 */
public class Adat63PlayerTypeField extends Field <Adat63PlayerTypeField>{

	public Adat63PlayerTypeField() {
		super("Player Type", 63);
		consume = new StringConsumeStrategy();
	}
	
}
