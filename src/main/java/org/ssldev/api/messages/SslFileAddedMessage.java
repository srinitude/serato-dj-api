package org.ssldev.api.messages;

import java.io.File;

import org.ssldev.core.messages.Message;

/**
 * SSL file that should be processed.  this is a one-time file add with no expectation
 * of follow up modification
 */
public class SslFileAddedMessage extends Message {

	public File sessionFile;

	public SslFileAddedMessage(String loc) {
		if (null == loc || loc.isEmpty())
			throw new IllegalArgumentException("SSL session file location given is null/empty");
		sessionFile = new File(loc);
		if (!sessionFile.exists() || !sessionFile.isFile())
			throw new IllegalArgumentException(
					"SSL session file does not exist or is not a file: [" + sessionFile.getAbsolutePath() + "]");
	}

	@Override
	public String toString() {
		return super.toString() + ": " + sessionFile.getName();
	}
}
