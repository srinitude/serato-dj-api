package org.ssldev.api.demo;

import org.ssldev.api.app.SslApi;
import org.ssldev.api.messages.NowInCueMessage;
import org.ssldev.api.messages.NowPlayingMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.services.ServiceIF;

/**
 * demonstrates usage of the ssl-api.
 */
public class UsageExample {

	public static void main(String[] args) {
		/*
		 * 1. instantiate the ssl-api
		 */
		SslApi api = new SslApi();
		
		/*
		 * 2. initialize the app (without supplying an event hub)
		 */
		api.init(null);
		
		/*
		 * 3. register a service and provide message types to subscribe to
		 */
		api.register(new ServiceIF() {

			@Override
			public void notify(MessageIF msg) {
				System.out.println("DEMO APP got notified of " + msg.getClass().getSimpleName() + ": "+msg);
			}

			@Override
			public void shutdown() {
				// null
			}

			@Override
			public String getName() {
				return "ssl api demo";
			}
			
			// subscribe for mostly single track changes, as simulated by the TrackPublisherService
		}, NowPlayingMessage.class, NowInCueMessage.class);

	}

}
