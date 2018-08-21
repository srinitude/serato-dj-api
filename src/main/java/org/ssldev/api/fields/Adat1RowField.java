package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.IntConsumeStrategy;

public class Adat1RowField extends Field<Adat1RowField> {

	public static final String ID = "1";
	
	public Adat1RowField() {
		super("Row",1);
		consume = new IntConsumeStrategy();
	}

}