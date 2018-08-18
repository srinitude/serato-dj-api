package org.ssldev.api.consumption.strategies;

import org.ssldev.api.consumption.SslBuffer;
import org.ssldev.core.utils.Logger;

public class BooleanConsumeStrategy extends ConsumeStrategy {

	@Override
	public void consume(SslBuffer buf) {
		// length
		int len = buf.size();
		Logger.finest(this, "length " + len);
		if(len != 1) throw new IllegalStateException("expected bool length to be 1. actual is "+len+".  prob wrong consume strategy");

		// value
		this.data = buf.readRawString(1).equals("1") ? true : false;
	}

}
