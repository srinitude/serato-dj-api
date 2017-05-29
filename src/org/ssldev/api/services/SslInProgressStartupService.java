package org.ssldev.api.services;

import java.util.Comparator;
import java.util.List;

import org.ssldev.api.chunks.Adat;
import org.ssldev.api.messages.AdatConsumedMessage;
import org.ssldev.api.messages.MultipleAdatsConsumedMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.Service;
import org.ssldev.core.utils.Logger;

/**
 * handles the case where NowPlaying starts after SSL and needs to
 * sort out what the latest played song is
 */
public class SslInProgressStartupService extends Service{

	public SslInProgressStartupService(EventHub hub) {
		super(hub);
	}

	@Override
	public void notify(MessageIF msg) {
		if(msg instanceof MultipleAdatsConsumedMessage) {
			MultipleAdatsConsumedMessage multiAdatsMsg = (MultipleAdatsConsumedMessage) msg;
			handle(multiAdatsMsg.adats);
		}
	}

	private void handle(List<Adat> adats) {
		Adat mostRecentAdat = adats.stream().sorted(Comparator.comparingInt(Adat::getRow)).reduce((a,b)-> b).orElse(null);
		if(null != mostRecentAdat) {
			Logger.debug(this, "most recent adat is " + mostRecentAdat.getTitle());
			hub.add(new AdatConsumedMessage(mostRecentAdat));
		} 
	}

}
