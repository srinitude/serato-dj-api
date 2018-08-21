package org.ssldev.api.messages;

import java.io.File;

import org.ssldev.api.consumption.SslByteConsumer;
import org.ssldev.core.messages.Message;
import org.ssldev.core.utils.Validate;

/**
 * represents historical session file bytes unmarshalled and stored in the consumer
 */
public class SessionBytesConsumedMessage extends Message {

	public final File sessionFile;
	public final SslByteConsumer byteConsumer;

	public SessionBytesConsumedMessage(SslByteConsumer byteConsumer, File sessionFile) {
		Validate.notNull(byteConsumer, "byte consumer cannot be null");
		Validate.notNull(sessionFile, "session file cannot be null");
		
		this.byteConsumer = byteConsumer;
		this.sessionFile = sessionFile;
	}

}
