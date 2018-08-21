package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.IntConsumeStrategy;

public class Adat45PlaytimeField extends Field <Adat3LocationField>{
	public static final String ID = "45";

	public Adat45PlaytimeField() {
		super("Playtime",45);
		consume = new IntConsumeStrategy();
	}
	
	@Override
	public String toString() {
		int secs = (int)data;
		int mins = secs/60; secs = secs %60;
		StringBuilder sb = new StringBuilder(name);
		sb.append(": ").append(String.format("%1$02d:%2$02d", mins,secs));
		return sb.toString();
	}

}
