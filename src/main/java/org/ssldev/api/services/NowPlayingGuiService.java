package org.ssldev.api.services;

import java.util.concurrent.atomic.AtomicInteger;

import org.ssldev.api.app.SslApi;
import org.ssldev.api.gui.NowPlayingAppGUI;
import org.ssldev.api.messages.NowInCueMessage;
import org.ssldev.api.messages.NowPlayingMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.AsyncEventHub;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.Service;
import org.ssldev.core.utils.Logger;

import javafx.application.Application;

public class NowPlayingGuiService extends Service {
	
	private AtomicInteger ctr = new AtomicInteger(1);

	public NowPlayingGuiService(EventHub hub) {
		super(hub);
		
		if(!(hub instanceof AsyncEventHub)) {
			Logger.error(this, "GUI service requires an async event hub. Service will not start.");
			return;
		}
		
		// only happens once...
		hub.invokeLater(NowPlayingGuiService::startGui);
	}
	
	private static void startGui() {
		Thread.currentThread().setName("THREAD - GUI launch service");
		// start the GUI
		try {
			Application.launch(NowPlayingAppGUI.class, new String[0]);
			// GUI has exited..
			doShutdown();
			Logger.info(NowPlayingGuiService.class, "GUI exit...");
		} catch(IllegalStateException e) {
			Logger.warn(NowPlayingGuiService.class, "cannot start SSL API GUI more than once.");
		}
	}

	@Override
	public void notify(MessageIF msg) {
		if(msg instanceof NowPlayingMessage) {
			NowPlayingMessage m = (NowPlayingMessage) msg;
			NowPlayingAppGUI.appendToNowPlaying(ctr.getAndIncrement() + ": "+m.adatForm);
			NowPlayingAppGUI.setCueLabelText(m.getArtist() + " - "+ m.getTitle());
		}
		else if(msg instanceof NowInCueMessage) {
			NowInCueMessage m = (NowInCueMessage) msg;
			NowPlayingAppGUI.appendToNowPlaying(ctr.getAndIncrement() + ": "+m.adatForm);
			NowPlayingAppGUI.setCueLabelText(m.toString());
		}
	}
	
	protected static void doShutdown() {
		// tell model to shutdown
		SslApi.shutdown();
	}

}
