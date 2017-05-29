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
import org.ssldev.core.mgmt.AsyncEventHub;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.FileWriterService;
import org.ssldev.core.services.ServiceIF;
import org.ssldev.core.utils.Logger;

public class SslApiApp {

	private String domFilePath = null;
	private String pathToSeratoFolder2 = null;
	private static String configFileName = "sslApiConfiguration.properties";
	private static boolean showGui = false;
	
	private static EventHub hub; 

	public static void main(String[] args) {
		SslApiApp m = new SslApiApp();
		
		// current threading model requires at least 3 threads (more threads may cause order issues as code is not totally thread safe):
		// 1 event thread for GUI
		// 1 SSL directory/file change listener
		// 1 general worker thread to service the blocking event queue
		m.init(new AsyncEventHub(3));
	}
	
	public void init(EventHub hub) {
		// laod the configuration
		AppConfig.load(configFileName);
		
		SslApiApp.hub = hub;
		
		// setup the logger
		Logger.enableDebug(AppConfig.getBooleanProperty(AppConfig.Property.ENABLE_DEBUG)); 
		Logger.enableFinest(AppConfig.getBooleanProperty(AppConfig.Property.ENABLE_FINEST)); 
		Logger.enableTrace(AppConfig.getBooleanProperty(AppConfig.Property.ENABLE_TRACE));
		Logger.setShowTime(AppConfig.getBooleanProperty(AppConfig.Property.SHOW_TIME));
		
		if(AppConfig.getBooleanProperty(AppConfig.Property.ENABLE_LOG_TO_FILE)) {
			String logFilePath = AppConfig.getStringProperty(AppConfig.Property.LOG_FILE_PATH);
			Logger.init(logFilePath);
		}
		
		domFilePath = AppConfig.getStringProperty(AppConfig.Property.DOM_FILE_PATH);
		pathToSeratoFolder2 = AppConfig.getStringProperty(AppConfig.Property.SSL_DIR_PATH);
		
		showGui = AppConfig.getBooleanProperty(AppConfig.Property.START_GUI);
		
		// register all app services
		registerAllServices(hub);
	}
	public static void shutdown() {
		hub.shutdown();
	}
	
	@SuppressWarnings("unused")
	private void registerAllServices(EventHub hub) {
		// create all services 
		SslFileReaderService readerService = new SslFileReaderService(hub);
		SslSessionFileUnmarshalService sslUnmarshalService = new SslSessionFileUnmarshalService(hub); 
		SslByteConsumerService consumerService = new SslByteConsumerService(hub);
		FileWriterService writerService = new FileWriterService(hub);
		DataConsumedPublisherService dataPubService = new DataConsumedPublisherService(hub);
		DomService dom = new DomService(hub, domFilePath);
		SslCurrentSessionFinderService sslFileService = new SslCurrentSessionFinderService(hub, pathToSeratoFolder2 );
		TrackPublisherService decks = new TrackPublisherService(hub);
		SslInProgressStartupService lateStartService = new SslInProgressStartupService(hub);
		
		// optional service to provide a GUI display for what songs
		// currently loaded/playing
		if(showGui) {
			NowPlayingGuiService gui = new NowPlayingGuiService(hub);
		}
	}
	
	public void register(ServiceIF externalService) {
		// TODO register by topics?  inbound vs outbound (external to app) messages...
		hub.register(externalService);
	}
}
