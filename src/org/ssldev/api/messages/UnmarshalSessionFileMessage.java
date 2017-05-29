package org.ssldev.api.messages;

import java.io.File;

import org.ssldev.core.messages.Message;
import org.ssldev.core.utils.Validate;
/**
 * represents request to unmarshal an SSL history session file
 * into collection of tracks 
 * 
 * TODO: this is more of a command than event/message. should refactor messages structure
 * into commands, events, messages, etc
 */
public class UnmarshalSessionFileMessage extends Message {
	public final File sessionFile;

	public UnmarshalSessionFileMessage(File sessionFile) {
		Validate.notNull(sessionFile, "session file cannot be null");
		Validate.isTrue(sessionFile.exists(), sessionFile.getAbsolutePath() + " must exist");
		
		this.sessionFile = sessionFile;
	}
}
