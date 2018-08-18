package org.ssldev.api.services;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.ssldev.api.messages.CrateConsumedMessage;
import org.ssldev.api.messages.CrateConvertRequestMessage;
import org.ssldev.api.messages.CrateConvertResponseMessage;
import org.ssldev.api.messages.SslFileAddedMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.AsyncEventHub;
import org.ssldev.core.services.Service;
import org.ssldev.core.utils.Logger;

/**
 * converts serato crate files into pojos
 */
public class CrateConvertService extends Service{

	public CrateConvertService(AsyncEventHub hub) {
		super(hub);
	}

	@Override
	public void notify(MessageIF msg) {
		Logger.trace(this, "notified of msg "+msg);
		
		if(msg instanceof CrateConvertRequestMessage) {
			convert((CrateConvertRequestMessage) msg);
		}
		else if(msg instanceof CrateConsumedMessage) {
			convert((CrateConsumedMessage) msg);
		}
	}

	private void convert(CrateConvertRequestMessage msg) 
	{
		Logger.trace(this.getClass(), "received: "+msg);
		
		SslFileAddedMessage m = 
				new SslFileAddedMessage(msg.seratoCrateFile.getAbsolutePath());
		
		this.hub.add(m);
	}
	
	private void convert(CrateConsumedMessage msg) {
		Logger.trace(this.getClass(), "got "+msg);
		
		String crateName = new File(msg.crateAbsolutePath).getName().replaceAll(".crate", "");
		
		List<String> displayedColumns = 
		msg.displayedColumns.stream()
			.map(tvcn -> tvcn.getData())
			.collect(Collectors.toList());
		
		List<String> crateTracksPaths = 
		msg.ptrks.stream()
			.map(ptrk -> toFilePath((String)ptrk.getData()))
			.collect(Collectors.toList());
			
		
		this.hub.add(new CrateConvertResponseMessage(crateName, displayedColumns, crateTracksPaths));
	}
	
	public static String toFilePath(String p) {
		//Users/elad/Desktop/Music/MacMusic/tracks/Muddy Waters  - Mannish Boy - LegalSounds  - 2012.mp3
		StringBuilder sb = new StringBuilder();
		sb.append("/");
		
		sb.append(p.replaceAll(" ", "\\ ").trim());
		
		return sb.toString();
	}

}
