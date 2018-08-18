package org.ssldev.api.chunks;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.strategies.StringConsumeStrategy;
/**
 * serato crate displayed column attribute (maybe width?)
 */
public class Tvcw extends ByteConsumer {

	public Tvcw() {
		super();
		id = name = "tvcw";
		consume = new StringConsumeStrategy();
	}
	
	@Override
	public ByteConsumer getInstance() {
		return new Tvcw();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.id); sb.append("\n");
		sb.append("\t").append(data);
		return sb.toString();
	}

}
