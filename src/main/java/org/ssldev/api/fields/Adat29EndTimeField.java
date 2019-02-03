package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.LongOrIntConsumeStrategy;
import org.ssldev.core.utils.SysInfo;

public class Adat29EndTimeField extends Field <Adat29EndTimeField >{

	public static final String ID = "29";

	public Adat29EndTimeField() {
		super("EndTime",29);
		consume = new LongOrIntConsumeStrategy();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name); 
		sb.append(": ").append(SysInfo.getDate((((Number)data).longValue()*1000)));
		return sb.toString();
	}

}
