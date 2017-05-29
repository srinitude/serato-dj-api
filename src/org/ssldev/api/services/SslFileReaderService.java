package org.ssldev.api.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.ssldev.api.consumption.SslBuffer;
import org.ssldev.api.messages.BytesReadyForConsumptionMessage;
import org.ssldev.api.messages.SslSessionFileAddedOrModifiedMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.Service;
import org.ssldev.core.utils.Logger;
/**
 * Reads in bytes from file into an SSL buffer to be consumed by others.
 * Keeps track of last read positions of each incoming file so that
 * only new bytes get passed in.
 */
public class SslFileReaderService extends Service {
	private HashMap<String,Integer> fileToLastByteProcd = new HashMap<>();

	public SslFileReaderService(EventHub hub) {
		super(hub);
	}

	@Override
	public void notify(MessageIF msg) {
		if(!(msg instanceof SslSessionFileAddedOrModifiedMessage)) return;
		
		SslSessionFileAddedOrModifiedMessage session = (SslSessionFileAddedOrModifiedMessage)msg;
		
		try {
			convertFileIntoByteBuffer(session.sessionFile);
		} catch (IOException e) {
			Logger.error(this, e.toString());
			e.printStackTrace();
		} catch(IllegalArgumentException iae) {
			Logger.warn(this, "Illegal argument exception (expected if happened at SSL shutdown due to file compacting: " + iae.getMessage());
		}
	}

	synchronized private void convertFileIntoByteBuffer(File historyFile) throws IOException {
		FileInputStream fs = new FileInputStream(historyFile);
		SslBuffer bytes = new SslBuffer();
		
		// add all bytes into buffer
		int b; 
		while((b = fs.read()) >= 0) {
			 bytes.add(b);
		}
		
		// compute start position if this file was already processed
		int len = bytes.size();
		int startAt = getStartPosition(historyFile.getName());
		bytes.fastforward(startAt);
		Logger.debug(this, historyFile.getName() + " contains " + bytes.size() + "/"+len+" (new/total) bytes.");
		
		fileToLastByteProcd.put(historyFile.getName(), len);
		fs.close();
		
		// notify that there are bytes ready to be consumed
		hub.add(new BytesReadyForConsumptionMessage(bytes, historyFile.getAbsolutePath()));
	}

	private int getStartPosition(String name) {
		return fileToLastByteProcd.containsKey(name) ? fileToLastByteProcd.get(name) : 0;
	}

}
