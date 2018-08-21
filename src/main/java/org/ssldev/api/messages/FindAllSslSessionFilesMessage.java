package org.ssldev.api.messages;

import org.ssldev.core.messages.Message;

/**
 * command message to find all SSL session files in given dir
 */
public class FindAllSslSessionFilesMessage extends Message {

	public String dir = null;
	
	public FindAllSslSessionFilesMessage(String dir) {
		this.dir = dir;
	}
}
