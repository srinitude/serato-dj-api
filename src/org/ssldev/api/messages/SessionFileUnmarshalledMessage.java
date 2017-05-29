package org.ssldev.api.messages;

import java.io.File;
import java.util.List;

import org.ssldev.api.chunks.Adat;
import org.ssldev.core.messages.Message;
import org.ssldev.core.utils.Validate;

/**
 * the session file was unmarshalled into the given list of tracks
 */
public class SessionFileUnmarshalledMessage extends Message {
	/** session file unmarshalled */
	public final File sessionFile;
	/** tracks unmarshalled from the session file */
	public final List<Adat> adats;

	public SessionFileUnmarshalledMessage(File sessionFile, List<Adat> adats) {
		Validate.notNull(sessionFile, "session file cannot be null");
		Validate.notNull(adats, "adats unmarshalled cannot be null");
		
		this.sessionFile = sessionFile;
		this.adats = adats;
	}
}
