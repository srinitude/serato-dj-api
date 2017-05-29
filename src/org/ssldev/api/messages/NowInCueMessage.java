package org.ssldev.api.messages;

import org.ssldev.api.chunks.Adat;
/**
 * a new track is in Cue notification
 */
public class NowInCueMessage extends NowPlayingMessage {

	public NowInCueMessage(Adat a, int deck) {
		super(a, deck);
	}

}
