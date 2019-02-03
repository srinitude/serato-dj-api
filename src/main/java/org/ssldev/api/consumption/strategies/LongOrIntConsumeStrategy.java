package org.ssldev.api.consumption.strategies;

import org.ssldev.api.consumption.SslBuffer;
import org.ssldev.core.utils.Logger;

/**
 * buffer consumption strategy for either a 4 bytes int or 8 bytes long primitives
 */
public class LongOrIntConsumeStrategy extends ConsumeStrategy {
	@Override
	public void consume(SslBuffer buf) {
		// length
		int len = buf.size();
		Logger.finest(this, "length " + len);

		// value
		if(len == 4)
			this.data = buf.readInt4();
		else if(len == 8)
			this.data = buf.readLong8();
		else
			throw new IllegalStateException("expected int/long length to be 4 or 8. actual is "+len+".  prob wrong consume strategy");
	}
}