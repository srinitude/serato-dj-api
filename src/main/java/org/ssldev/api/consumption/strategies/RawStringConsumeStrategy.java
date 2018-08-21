package org.ssldev.api.consumption.strategies;

import org.ssldev.api.consumption.SslBuffer;

public class RawStringConsumeStrategy extends ConsumeStrategy {

	@Override
	public void consume(SslBuffer buf) {
		data = buf.readRawString(buf.size());
	}
}
