package org.ssldev.api.chunks;

import java.util.List;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.strategies.CompoundChunkConsumeStrategy;
/**
 * 1:
 * id: 'oent'
 * 2:
 * int: length
 * 3: 'adat'
 */
public class Oent extends ByteConsumer {
	
	public Oent() {
		super();
		id = name = "oent";
		
		consume = new CompoundChunkConsumeStrategy(this);
		constructOent();
	}

	private void constructOent() {
		register(new Adat());
	}

	@Override
	public ByteConsumer getInstance() {
		return new Oent();
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
