package org.ssldev.api.messages;

import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.core.messages.Message;

/**
 * a chunk registers for processing 
 */
public class ByteConsumerRegistrationMessage extends Message {
	public ByteConsumerIF consumer;
	
	public ByteConsumerRegistrationMessage(ByteConsumerIF u) {
		if(null == u)throw new IllegalArgumentException("cannot register empty chunk.");
		consumer = u;
	}
	
	@Override
	public String toString() {
		return super.toString() + consumer.getId();
	}
	
}
