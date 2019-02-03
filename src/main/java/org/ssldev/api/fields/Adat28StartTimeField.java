package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.LongOrIntConsumeStrategy;
import org.ssldev.core.utils.SysInfo;

public class Adat28StartTimeField extends Field <Adat28StartTimeField >{
	public static final String ID = "28";

	public Adat28StartTimeField() {
		super("StartTime",28);
		consume = new LongOrIntConsumeStrategy();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name); 
		sb.append(": ").append(SysInfo.getDate((((Number)data).longValue()*1000)));
		return sb.toString();
	}

}
