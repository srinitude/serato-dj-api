package org.ssldev.api.consumption.strategies;

import org.ssldev.api.consumption.SslBuffer;

public class StringConsumeStrategy extends ConsumeStrategy {
	@Override
	public void consume(SslBuffer buf) {
		data = buf.readString(buf.size());
	}
}
