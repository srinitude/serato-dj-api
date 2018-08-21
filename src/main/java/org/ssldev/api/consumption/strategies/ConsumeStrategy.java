package org.ssldev.api.consumption.strategies;

import org.ssldev.api.consumption.SslBuffer;

/**
 * biz logic that knows how to consume a buffer. 
 */
public abstract class ConsumeStrategy {
	int length; 
	Object data;

	public abstract void consume(SslBuffer buf);
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
		if(null != data) sb.append("\n").append(data);
		return sb.toString();
	}

	public Object getData() {
		return data;
	}

}
