package org.ssldev.api.fields;

import org.ssldev.api.consumption.strategies.LongOrIntConsumeStrategy;
import org.ssldev.core.utils.SysInfo;

public class Adat45PlaytimeField extends Field <Adat3LocationField>{
	public static final String ID = "45";

	public Adat45PlaytimeField() {
		super("Playtime",45);
		consume = new LongOrIntConsumeStrategy();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		long secs = ((Number)data).longValue();
		sb.append(": ").append(SysInfo.getTimeInMinutesSeconds(secs * 1000));
		return sb.toString();
	}

}
