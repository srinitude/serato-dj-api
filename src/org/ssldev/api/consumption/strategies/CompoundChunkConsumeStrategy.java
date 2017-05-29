package org.ssldev.api.consumption.strategies;

import java.util.ArrayList;
import java.util.List;

import org.ssldev.api.chunks.UnknownUnk;
import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.IntString;
import org.ssldev.api.consumption.SslBuffer;
import org.ssldev.core.utils.Logger;

public class CompoundChunkConsumeStrategy extends ConsumeStrategy {

	private ByteConsumer consumer;
	private List<ByteConsumerIF> consumers = new ArrayList<>();
	
	public CompoundChunkConsumeStrategy(ByteConsumer c) {
		consumer = c;
	}
	
	public void consume(SslBuffer bytes) {
		while(!bytes.isEmpty()) {
			// key may be int or string interpretation 
			IntString key = new IntString(bytes.remove(4)); 
			int len = bytes.readInt4();
			SslBuffer buf = bytes.remove(len);
			
			if(consumer.isRegistered(key)) {
				ByteConsumerIF subConsumer = consumer.buildConsumer(key);
				subConsumer.consume(buf);
				consumers.add(subConsumer);
			} else {
				consumeUknown(key, buf);
			}
			
			Logger.finest(this, consumer.getName() + ": buffer size now " + bytes.size());
		}
		data = consumers;
	}
	
	private void consumeUknown(IntString key, SslBuffer bytes) {
		Logger.warn(this, "encountered unknown registration id:" + key);
		UnknownUnk u = new UnknownUnk(); 
		u.consume(bytes); // TODO should this be published instead of tossed?
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nbelongs to " + consumer.getName();
	}
	
}
