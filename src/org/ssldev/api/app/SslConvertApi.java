package org.ssldev.api.app;

import java.io.File;

import org.ssldev.api.messages.CrateConvertRequestMessage;
import org.ssldev.api.services.CrateConvertService;
import org.ssldev.api.services.CrateDataConsumedPublisherService;
import org.ssldev.api.services.SslByteConsumerService;
import org.ssldev.api.services.SslFileReaderService;
import org.ssldev.api.services.TrackPublisherService;
import org.ssldev.core.mgmt.AsyncEventHub;
import org.ssldev.core.utils.Logger;
import org.ssldev.core.utils.SysInfo;

/**
 * API for converting SSL binary files into POJOs
 */
public class SslConvertApi {

	final private AsyncEventHub hub;

	public static void main(String[] args) {
		AsyncEventHub hub = new AsyncEventHub(2);
		SslConvertApi api = new SslConvertApi(hub);
		
		System.out.println(SysInfo.getUserHomeLoc() + "Desktop/test/test.crate");
		File histFile = new File(SysInfo.getUserHomeLoc() + "Desktop/test/test.crate");
		System.out.println(histFile.exists());
		CrateConvertRequestMessage c = new CrateConvertRequestMessage(histFile);
		hub.add(c);
	}
	
	public SslConvertApi(AsyncEventHub hub) {
		this.hub = hub;
		
		Logger.enableDebug(true); 
		Logger.enableFinest(false); 
		Logger.enableTrace(false);
		Logger.setShowTime(true);
		
		registerAllservices();
	}

	@SuppressWarnings("unused")
	private void registerAllservices() {
		CrateConvertService crateConvertService = new CrateConvertService(hub); 
		SslFileReaderService sslFileReader = new SslFileReaderService(hub);
		TrackPublisherService trackPublisher = new TrackPublisherService(hub);
		SslByteConsumerService byteConsumer = new SslByteConsumerService(hub);
		CrateDataConsumedPublisherService dataConsumer = new CrateDataConsumedPublisherService(hub);
	}
}
