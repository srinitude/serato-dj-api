package org.ssldev.api.chunks;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.SslBuffer;

/**
 * 1: id -> 'vrsn'
 * 2: version field
 */
public class Vrsn extends ByteConsumer {
	/**
	 * ex: 1.0/Serato Scratch LIVE Review
	 */
	public String version;
	
	public Vrsn() {
		super();
		id = name = "vrsn";
	}
	
	@Override
	public ByteConsumer getInstance() {
		return new Vrsn();
	}
	
	@Override
	public void consume(SslBuffer buf) {
		version = buf.readString(buf.size());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.id); sb.append("\n");
		sb.append("\t").append(version);
		return sb.toString();
	}
	
}
