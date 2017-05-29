package org.ssldev.api.messages;

import java.io.File;

import org.ssldev.api.consumption.SslBuffer;
import org.ssldev.core.messages.Message;
import org.ssldev.core.utils.Validate;

/**
 * contains the bytes of an historical session file (non-changing) in its 
 * buffer and indicates that the buffer is ready to be consumed and converted.  
 */
public class SessionBytesReadyForConsumtionMessage extends Message {

	public final SslBuffer buffer;
	public final File sessionFile;

	public SessionBytesReadyForConsumtionMessage(SslBuffer bytes, File sessionFile) {
		Validate.notNull(bytes, "byte buffer cannot be null");
		Validate.isFalse(bytes.isEmpty(), "byte buffer should not be empty");
		Validate.notNull(sessionFile, "session file cannot be null");
		Validate.isTrue(sessionFile.exists(), sessionFile.getAbsolutePath() + " does not exist");
		
		this.buffer = bytes;
		this.sessionFile = sessionFile;
	}

}
