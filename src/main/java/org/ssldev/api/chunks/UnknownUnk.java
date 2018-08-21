package org.ssldev.api.chunks;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.SslBuffer;

public class UnknownUnk extends ByteConsumer {
	private String data;
	
	public UnknownUnk() {
		super();
		name = "Unknown Chunk";
		id = "unkn";
	}

	@Override
	public void consume(SslBuffer bytes) {
		data = bytes.readRawString(bytes.size());
	}
	
	public String getData() {
		return data;
	}

	@Override
	public ByteConsumerIF getInstance() {
		return new UnknownUnk();
	}

}
