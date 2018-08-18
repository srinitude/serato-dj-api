package org.ssldev.api.consumption;

import java.util.List;

import org.ssldev.api.chunks.Oent;
import org.ssldev.api.chunks.Osrt;
import org.ssldev.api.chunks.Otrk;
import org.ssldev.api.chunks.Ovct;
import org.ssldev.api.chunks.Vrsn;
import org.ssldev.api.consumption.strategies.CompoundChunkConsumeStrategy;

public class SslByteConsumer extends ByteConsumer {
	
	public SslByteConsumer() {
		name = "SSL Data Consumer";
		constructConsumer();
		
		consume = new CompoundChunkConsumeStrategy(this);
	}
	
	private void constructConsumer() {
		register(new Oent());
		register(new Vrsn());
		register(new Osrt());
		register(new Ovct());
		register(new Otrk());
	}

	@Override
	public ByteConsumerIF getInstance() {
		return new SslByteConsumer();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ByteConsumerIF> getData() {
		return (List<ByteConsumerIF>) data;
	}

}
