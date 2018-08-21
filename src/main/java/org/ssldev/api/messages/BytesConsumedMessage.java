package org.ssldev.api.messages;

import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.core.messages.Message;
/**
 * Bytes consumed and ready for access notification.  
 */
public class BytesConsumedMessage extends Message {
	public ByteConsumerIF consumer;
	public String fileName;
	
	public BytesConsumedMessage(ByteConsumerIF u, String fileName) {
		consumer = u;
		this.fileName = fileName;
	}
	
	@Override
	public String toString() {
		return super.toString() + consumer.getId() + " read in.";
	}
}
