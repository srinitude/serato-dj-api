package org.ssldev.api.fields;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ssldev.api.consumption.strategies.IntConsumeStrategy;

public class Adat28StartTimeField extends Field <Adat28StartTimeField >{
	public static final String ID = "28";

	public Adat28StartTimeField() {
		super("StartTime",28);
		consume = new IntConsumeStrategy();
	}
	
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name); 
		sb.append(": ").append(sdf.format(new Date(((Integer)data).longValue()*1000)));
		return sb.toString();
	}

}
