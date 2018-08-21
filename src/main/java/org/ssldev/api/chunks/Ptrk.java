package org.ssldev.api.chunks;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.strategies.StringConsumeStrategy;
/**
 * Serato crate track pointer.  data gets the absolute path of a file
 */
public class Ptrk extends ByteConsumer{
	
	public Ptrk() {
		super();
		id = name = "ptrk";
		
		consume = new StringConsumeStrategy();
	}

	@Override
	public ByteConsumer getInstance() {
		return new Ptrk();
	}
	
	@Override
	public String getData() {
		return (String) data;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\n");
			sb.append("\t").append(data);
		return sb.toString();
	}
}
