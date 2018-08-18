package org.ssldev.api.chunks;

import java.util.List;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.strategies.CompoundChunkConsumeStrategy;
/**
 * serato crate track pointer wrapper
 */
public class Otrk extends ByteConsumer {

	public Otrk() {
		super();
		id = name = "otrk";
		
		consume = new CompoundChunkConsumeStrategy(this);
		constructOtrk();
	}

	private void constructOtrk() {
		register(new Ptrk());
	}

	@Override
	public ByteConsumer getInstance() {
		return new Otrk();
	}
	
	@Override
	@SuppressWarnings("unchecked")
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
