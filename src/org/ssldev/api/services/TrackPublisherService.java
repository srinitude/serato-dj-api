package org.ssldev.api.services;

import java.util.HashMap;
import java.util.Map;

import org.ssldev.api.chunks.Adat;
import org.ssldev.api.messages.AdatConsumedMessage;
import org.ssldev.api.messages.NowInCueMessage;
import org.ssldev.api.messages.NowPlayingMessage;
import org.ssldev.api.messages.TrackInDeckUpdatedMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.Service;
import org.ssldev.core.utils.Logger;
/**
 * publishes what tracks are playing and get loaded on a set of decks.
 */
public class TrackPublisherService extends Service {
	Map<Integer,Deck> decks = new HashMap<>();
	
	/** ensure serial track processing */
	final Boolean lock = new Boolean(true);

	public TrackPublisherService(EventHub hub) {
		super(hub);
	}
	
	public void notify(MessageIF msg) {
		if(!(msg instanceof AdatConsumedMessage)) return;
		
		synchronized (lock) {
			process(((AdatConsumedMessage)msg).adat);
		}
	}

	private void process(Adat adat) {
		int deckId = adat.getDeck();
		Deck deck = decks.get(deckId);
		if(deck == null) decks.put(deckId, new Deck(deckId));
		deck = decks.get(deckId);
		deck.onChange(adat);
	}

	private class Deck{
		private final int deck;
		private Adat playin;
		private Adat inCue;
		
		public Deck(int num) {
			deck = num;
		}

		/**
		 * notification that an adat update was received for 
		 * this deck
		 * @param adat
		 */
		public void onChange(Adat adat) {
			/*
			 * 1. cue tack removed
			 * 2. cue track added
			 * 3. track playing add / playing update
			 */
			// is cued track?
			if(!adat.isPlayed()) onCueChange(adat);
			else onPlayChange(adat);
		}

		private void onCueChange(Adat adat) {
			if(null == inCue || !adat.getTitle().equals(inCue.getTitle())) {
//				Logger.info(this, toString() + " - New inCue - ] " + adat.getTitle());
				inCue = adat;
				publishInCue(adat);
			}
			else {
				publishTrackUpdated(adat);
			}
		}

		private void onPlayChange(Adat adat) {
			if(null == playin || !adat.getTitle().equals(playin.getTitle())) {
//				Logger.info(this, toString() + " - New Play - ] " + adat.getTitle());
				playin = adat;
				publishNowPlaying(adat);
			}
			else {
				publishTrackUpdated(adat);
			}
		}
		
		private void publishInCue(Adat adat) {
			Logger.info(this, toString() + "[InCue] "+adat.getArtist() +" - " +adat.getTitle()+" ["+adat.getBpm()+"]");
			Logger.debug(this, adat.toString());
			hub.add(new NowInCueMessage(adat, deck));
		}

		private void publishNowPlaying(Adat adat) {
			Logger.info(this, toString() + "[Playing] "+adat.getArtist() +" - " +adat.getTitle() + " ["+adat.getBpm()+"]");
			Logger.debug(this, adat.toString());
			hub.add(new NowPlayingMessage(adat, deck));
		}

		private void publishTrackUpdated(Adat adat) {
			Logger.info(this, toString() + "[Update] "+adat.getArtist() +" - " +adat.getTitle() + " ["+adat.getBpm()+"] : "
					+ " s.time "+adat.getStartTime()+", e.time "+adat.getEndTime()+", u.time " + adat.getUpdateTime());
			Logger.debug(this, adat.toString());
			hub.add(new TrackInDeckUpdatedMessage(adat, deck));
		}
		
		@Override
		public String toString() {
			return "[DECK "+deck+"]";
		}
	}

	@Override
	public void shutdown() {
		super.shutdown();
		decks.clear();
	}
}
