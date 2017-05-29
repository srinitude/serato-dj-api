package org.ssldev.api.messages;

import org.ssldev.api.consumption.SslBuffer;
import org.ssldev.core.messages.Message;

/**
 * contains new bytes read in from file. 
 */
public class BytesReadyForConsumptionMessage extends Message {
	public SslBuffer bytes;
	public String fileName;

	public BytesReadyForConsumptionMessage(SslBuffer bytes, String file) {
		if(null == bytes) throw new IllegalArgumentException("null bytes provided");
		this.bytes = bytes;
		this.fileName = file;
	}
	
	@Override
	public String toString() {
		return super.toString() + "bytes size " +bytes.size();
	}

}
