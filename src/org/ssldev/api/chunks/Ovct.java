package org.ssldev.api.chunks;

import java.util.List;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.strategies.CompoundChunkConsumeStrategy;
/**
 * 
 * serato crate file column info wrapper
 */
public class Ovct extends ByteConsumer{

	public Ovct() {
		super();
		id = name = "ovct";
		
		consume = new CompoundChunkConsumeStrategy(this);
		constructOvct();
	}

	private void constructOvct() {
		register(new Tvcn());
		register(new Tvcw());
		register(new Brev());
	}

	@Override
	public ByteConsumer getInstance() {
		return new Ovct();
	}
	
	@Override
	public List<ByteConsumerIF> getData() {
		return (List<ByteConsumerIF>) data;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\n");
		for(ByteConsumerIF b : getData()) {
			sb.append("\t").append(b).append("\n");
		}
		return sb.toString();
	}
}
