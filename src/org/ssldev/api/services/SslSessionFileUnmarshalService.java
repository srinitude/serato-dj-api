package org.ssldev.api.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.ssldev.api.chunks.Adat;
import org.ssldev.api.chunks.Oent;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.SslBuffer;
import org.ssldev.api.consumption.SslByteConsumer;
import org.ssldev.api.messages.SessionBytesConsumedMessage;
import org.ssldev.api.messages.SessionBytesReadyForConsumtionMessage;
import org.ssldev.api.messages.SessionFileUnmarshalledMessage;
import org.ssldev.api.messages.UnmarshalSessionFileMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.Service;
import org.ssldev.core.utils.Logger;
/**
 * unmarshals an SSL history session file into bytes.<p> 
 * 
 * input:<br>{@link UnmarshalSessionFileMessage}<br>
 * 		     {@link SessionBytesConsumedMessage}<br>
 * oputput:<br> {@link SessionBytesReadyForConsumtionMessage}<br>
 * 			{@link SessionFileUnmarshalledMessage}
 */
public class SslSessionFileUnmarshalService extends Service {
	private Set<String> sessionsToUnmarshal = new HashSet<>();

	public SslSessionFileUnmarshalService(EventHub hub) {
		super(hub);
	}

	@Override
	public void notify(MessageIF msg) {
		if(msg instanceof UnmarshalSessionFileMessage) {
			File historyFile = ((UnmarshalSessionFileMessage) msg).sessionFile;
			
			Logger.info(this, "attempting to unmarshal " + historyFile.getAbsolutePath());
			try {
				convertFileIntoByteBuffer(historyFile);
			} catch (IOException ioe) {
				Logger.error(this, ioe.toString());
			}
		}
		else if(msg instanceof SessionBytesConsumedMessage) {
			SessionBytesConsumedMessage sessBytes = (SessionBytesConsumedMessage) msg;
			if(sessionsToUnmarshal.contains(sessBytes.sessionFile.getName())){
				List<Adat> adats = convertToAdats(sessBytes.byteConsumer);
//				List<TrackIF> tracks = convertToTracks(sessBytes.byteConsumer);
				Logger.info(this, "finished unmarshalling " + sessBytes.sessionFile.getAbsolutePath());
				sessionsToUnmarshal.remove(sessBytes.sessionFile.getName());
				
				hub.add(new SessionFileUnmarshalledMessage(sessBytes.sessionFile, adats));
//				hub.add(new SessionFileUnmarshalledMessage(sessBytes.sessionFile, tracks));
			}
		}
	}

	private List<Adat> convertToAdats(SslByteConsumer ssl) {
		/*
		 * currently structure is as follows:
		 * [1]ssl
		 * -->[1] vrsn
		 * -->[*] oent
		 * 			-->[*] adat
		 * 						--> [*] field 
		 */
		List<Adat> adats = new LinkedList<>(); 
		for(ByteConsumerIF c : ssl.getData()) {
			if(c instanceof Oent) {
				Oent oent = (Oent) c;
				for (ByteConsumerIF oentData : oent.getData()) {
					if(oentData instanceof Adat) {
						adats.add((Adat) oentData);
					} 
				}
			} 
		}

		return adats;
//		return convertAdatsToTracks(adats);
	}

//	private List<TrackIF> convertAdatsToTracks(List<Adat> adats) {
//		List<TrackIF> tracks = new LinkedList<>();
//		adats.forEach(a-> tracks.add(TrackConverter.from(a)));
//		return tracks;
//	}

	private void convertFileIntoByteBuffer(File historyFile) throws IOException{
		FileInputStream fs = new FileInputStream(historyFile);
		SslBuffer bytes = new SslBuffer();
		
		// add all bytes into buffer
		int b; 
		while((b = fs.read()) >= 0) {
			 bytes.add(b);
		}
		
		fs.close();
		
		// await unmarshalling results
		// TODO refactor into an action instead of message, so that time-out,
		// no proc, and other exceptions can be handled and prevent leaks
		sessionsToUnmarshal.add(historyFile.getName());
		hub.add(new SessionBytesReadyForConsumtionMessage(bytes, historyFile));
	}

}
