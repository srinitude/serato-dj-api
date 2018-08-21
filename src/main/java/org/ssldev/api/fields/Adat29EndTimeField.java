package org.ssldev.api.fields;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ssldev.api.consumption.strategies.IntConsumeStrategy;

public class Adat29EndTimeField extends Field <Adat29EndTimeField >{

	public static final String ID = "29";

	public Adat29EndTimeField() {
		super("EndTime",29);
		consume = new IntConsumeStrategy();
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name); 
		sb.append(": ").append(sdf.format(new Date(((Integer)(data)).longValue()*1000)));
		return sb.toString();
	}

}
