package org.ssldev.api.services;

import org.ssldev.api.consumption.SslBuffer;
import org.ssldev.api.consumption.SslByteConsumer;
import org.ssldev.api.messages.BytesConsumedMessage;
import org.ssldev.api.messages.BytesReadyForConsumptionMessage;
import org.ssldev.api.messages.SessionBytesConsumedMessage;
import org.ssldev.api.messages.SessionBytesReadyForConsumtionMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.Service;
/**
 * converts bytes to chunks.  Chunks should register with this service to process
 * bytes.  Assumes only one chunk per ID.  Dumps data whose ID is not known to the service
 */
public class SslByteConsumerService extends Service {
//	private SslByteConsumer byteConsumer;

	public SslByteConsumerService(EventHub hub) {
		super(hub);
		
	}

	@Override
	public void notify(MessageIF msg) {
		if(msg instanceof BytesReadyForConsumptionMessage) {
			BytesReadyForConsumptionMessage bytesMsg = (BytesReadyForConsumptionMessage) msg;
			if(bytesMsg.bytes.isEmpty()) {
				return;
			}
			
			SslByteConsumer byteConsumer = consumeBuffer(bytesMsg.bytes);
			// publish the consumption
			hub.add(new BytesConsumedMessage(byteConsumer, bytesMsg.fileName));
		} 
		else if(msg instanceof SessionBytesReadyForConsumtionMessage) {
			SessionBytesReadyForConsumtionMessage sessionBytesMsg = (SessionBytesReadyForConsumtionMessage) msg;
			if(sessionBytesMsg.buffer.isEmpty()) {
				return;
			}
			
			SslByteConsumer byteConsumer = consumeBuffer(sessionBytesMsg.buffer);
			// publish the consumption
			hub.add(new SessionBytesConsumedMessage(byteConsumer, sessionBytesMsg.sessionFile));
		}
	}
	
	private SslByteConsumer consumeBuffer(SslBuffer bytes) {
		SslByteConsumer byteConsumer = new SslByteConsumer();
		while(!bytes.isEmpty()) {
			byteConsumer.consume(bytes);
		}
		return byteConsumer;
	}
}
