package org.ssldev.api.consumption.strategies;

import org.ssldev.api.consumption.SslBuffer;

public class OneByteConsumeStrategy extends ConsumeStrategy {
	
	@Override
	public void consume(SslBuffer buf) {
		int len = buf.size(); 
		if(len != 1) throw new IllegalStateException("assumed always length of 1. instead length == " +len);
		this.data = buf.poll();
	}

}
