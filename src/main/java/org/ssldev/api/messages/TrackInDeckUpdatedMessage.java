package org.ssldev.api.messages;

import org.ssldev.api.chunks.Adat;
import org.ssldev.core.messages.MessageIF;

/**
 * Cueued or now playing track got updated
 */
public class TrackInDeckUpdatedMessage implements MessageIF {
	
	public final int deck;
	public final Adat adat;

	/**
	 * @param adat track updated
	 * @param deck deck track is loaded on
	 */
	public TrackInDeckUpdatedMessage(Adat adat, int deck) {
		this.adat = adat;
		this.deck = deck;
	}

}
