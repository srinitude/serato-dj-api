package org.ssldev.api.chunks;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.strategies.StringConsumeStrategy;
/**
 * serato crate displayed column name  
 */
public class Tvcn extends ByteConsumer{

	public Tvcn() {
		super();
		id = name = "tvcn";
		
		consume = new StringConsumeStrategy();;	
	}

	@Override
	public ByteConsumer getInstance() {
		return new Tvcn();
	}
	
	@Override
	public String getData() {
		return (String) data;
	}
	
	@Override
	public String toString() {
		return data.toString();
	}

}
