package org.ssldev.api.app;

import org.ssldev.api.services.DataConsumedPublisherService;
import org.ssldev.api.services.DomService;
import org.ssldev.api.services.NowPlayingGuiService;
import org.ssldev.api.services.SslByteConsumerService;
import org.ssldev.api.services.SslCurrentSessionFinderService;
import org.ssldev.api.services.SslFileReaderService;
import org.ssldev.api.services.SslInProgressStartupService;
import org.ssldev.api.services.SslSessionFileUnmarshalService;
import org.ssldev.api.services.TrackPublisherService;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.AsyncEventHub;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.FileWriterService;
import org.ssldev.core.services.ServiceIF;
import org.ssldev.core.utils.Logger;

public class SslApi {

	private String domFilePath = null;
	private String pathToSeratoFolder = null;
	private static String configFileName = "sslApiConfiguration.properties";
	private static boolean showGui = false;
	
	private static SslApi instance = null;
	private EventHub hub; 

	public static void main(String[] args) {
		SslApi m = new SslApi();

		m.init(new AsyncEventHub(3));
	}
	
	public void init(EventHub hub) {
		// laod the configuration
		SslApiConfig.load(configFileName);

		showGui = SslApiConfig.getBooleanProperty(SslApiConfig.Property.START_GUI);
		
		// current threading model requires 3 threads (more threads may cause out-of-order notifications; current implementation does not guarantee order)
		// 1 event thread for GUI
		// 1 SSL directory/file change listener
		// 1 general worker thread to service the blocking event queue
		int numThreads = showGui? 3 : 2;
		
		// defaultify the event hub if none is provided
		this.hub = hub == null? new AsyncEventHub(numThreads) : hub;
		
		// setup the logger
		Logger.enableDebug(SslApiConfig.getBooleanProperty(SslApiConfig.Property.ENABLE_DEBUG)); 
		Logger.enableFinest(SslApiConfig.getBooleanProperty(SslApiConfig.Property.ENABLE_FINEST)); 
		Logger.enableTrace(SslApiConfig.getBooleanProperty(SslApiConfig.Property.ENABLE_TRACE));
		Logger.setShowTime(SslApiConfig.getBooleanProperty(SslApiConfig.Property.SHOW_TIME));
		
		Logger.info(this, "Running with the following properties:" + System.lineSeparator() + SslApiConfig.instance.toString());
		
		if(SslApiConfig.getBooleanProperty(SslApiConfig.Property.ENABLE_LOG_TO_FILE)) {
			String logFilePath = SslApiConfig.getStringProperty(SslApiConfig.Property.LOG_FILE_PATH);
			Logger.init(logFilePath);
		}
		
		domFilePath = SslApiConfig.getStringProperty(SslApiConfig.Property.DOM_FILE_PATH);
		pathToSeratoFolder = SslApiConfig.getStringProperty(SslApiConfig.Property.SSL_DIR_PATH);
		
		SslApi.instance = this;
		
		// register all app services
		registerAllServices(this.hub);
	}
	
	/**
	 * allow shutdown of api
	 */
	public static void shutdown() {
		if(null != SslApi.instance) {
			SslApi.instance.hub.shutdown();
		}
	}
	
	@SuppressWarnings("unused")
	private void registerAllServices(EventHub hub) {
		Logger.info(this, "Registering all API services:");
		
		// create all services 
		SslFileReaderService readerService = new SslFileReaderService(hub);
		SslSessionFileUnmarshalService sslUnmarshalService = new SslSessionFileUnmarshalService(hub); 
		SslByteConsumerService consumerService = new SslByteConsumerService(hub);
		FileWriterService writerService = new FileWriterService(hub);
		DataConsumedPublisherService dataPubService = new DataConsumedPublisherService(hub);
		DomService dom = new DomService(hub, domFilePath);
		SslCurrentSessionFinderService sslFileService = new SslCurrentSessionFinderService(hub, pathToSeratoFolder );
		TrackPublisherService decks = new TrackPublisherService(hub);
		SslInProgressStartupService lateStartService = new SslInProgressStartupService(hub);
		
		// optional service to provide a GUI display for what songs
		// currently loaded/playing
		if(showGui) {
			NowPlayingGuiService gui = new NowPlayingGuiService(hub);
		}
	}
	
	/**
	 * register this service to be notified of given message/event set.  If no messages are given, the service
	 * will be notified of all messages/events.
	 * 
	 * @param externalService to notify
	 * @param msgs optional set of messags to be notified of
	 */
	@SafeVarargs
	public final void register(ServiceIF externalService, Class<? extends MessageIF>... msgs){
		hub.register(externalService, msgs);
	}
}
