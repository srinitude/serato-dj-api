package org.ssldev.api.messages;

import java.io.File;

import org.ssldev.core.messages.Message;
/**
 * represents a binary SSL session file 
 * @author Elad
 *
 */
public class SslSessionFileAddedOrModifiedMessage extends Message {
	public File sessionFile;
	
	public SslSessionFileAddedOrModifiedMessage(String loc) {
		if(null == loc || loc.isEmpty()) 
			throw new IllegalArgumentException("SSL session file location given is null/empty");
		sessionFile = new File(loc);
		if(!sessionFile.exists() || !sessionFile.isFile())
			throw new IllegalArgumentException("SSL session file does not exist or is not a file: ["+sessionFile.getAbsolutePath()+"]");

		// valid.. we're good to continue
	}
	
	@Override
	public String toString() {
		return super.toString() + ": "+sessionFile.getName();
	}

}
