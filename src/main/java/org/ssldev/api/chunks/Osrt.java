package org.ssldev.api.chunks;

import java.util.List;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.strategies.CompoundChunkConsumeStrategy;
/**
 * compound chunk that is part of the crate file structure. contains a collection of TVCN, TVCW, and BREV
 */
public class Osrt extends ByteConsumer {

	public Osrt() {
		super();
		id = name = "osrt";
		
		consume = new CompoundChunkConsumeStrategy(this);
		constructOsrt();
	}

	private void constructOsrt() {
		register(new Tvcn());
		register(new Tvcw());
		register(new Brev());
	}

	@Override
	public ByteConsumer getInstance() {
		return new Osrt();
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
