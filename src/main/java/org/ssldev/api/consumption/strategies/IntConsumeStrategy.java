package org.ssldev.api.consumption.strategies;

import org.ssldev.api.consumption.SslBuffer;
import org.ssldev.core.utils.Logger;

public class IntConsumeStrategy extends ConsumeStrategy {
	@Override
	public void consume(SslBuffer buf) {
		// length
		int len = buf.size();
		Logger.finest(this, "length " + len);
		if(len != 4) throw new IllegalStateException("expected int length to be 4. actual is "+len+".  prob wrong consume strategy");

		// value
		this.data = buf.readInt4();
	}
}
