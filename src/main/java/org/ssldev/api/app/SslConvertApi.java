package org.ssldev.api.app;

import org.ssldev.api.services.CrateConvertService;
import org.ssldev.api.services.CrateDataConsumedPublisherService;
import org.ssldev.api.services.SslByteConsumerService;
import org.ssldev.api.services.SslFileReaderService;
import org.ssldev.api.services.TrackPublisherService;
import org.ssldev.core.mgmt.AsyncEventHub;
import org.ssldev.core.utils.Logger;

/**
 * API for converting SSL binary files into POJOs
 */
public class SslConvertApi {

	public SslConvertApi(AsyncEventHub hub) {
		
		Logger.enableDebug(false); 
		Logger.enableFinest(false); 
		Logger.enableTrace(false);
		Logger.setShowTime(true);
		
		registerAllservices(hub);
	}

	@SuppressWarnings("unused")
	private void registerAllservices(AsyncEventHub hub) {
		CrateConvertService crateConvertService = new CrateConvertService(hub); 
		SslFileReaderService sslFileReader = new SslFileReaderService(hub);
		TrackPublisherService trackPublisher = new TrackPublisherService(hub);
		SslByteConsumerService byteConsumer = new SslByteConsumerService(hub);
		CrateDataConsumedPublisherService dataConsumer = new CrateDataConsumedPublisherService(hub);
	}
}
