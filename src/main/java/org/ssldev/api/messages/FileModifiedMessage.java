package org.ssldev.api.messages;

import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ssldev.core.messages.Message;

/**
 * notification that the file parameter has been modified 
 */
public class FileModifiedMessage extends Message {
	/** time file was last modified at the time this message was created*/
	public long lastModTime;
	/** file that was modified */
	public File historyFile;
	
	private static Date date = new Date(System.currentTimeMillis());
	private static Format format = new SimpleDateFormat("HH:mm:ss");

	public FileModifiedMessage(File modFile, long lastModTime) {
		this.historyFile = modFile;
		this.lastModTime = lastModTime;
	}
	
	@Override
	public String toString() {
		date.setTime(lastModTime);
		return super.toString() + "mod time " + format.format(date); 
	}
}
