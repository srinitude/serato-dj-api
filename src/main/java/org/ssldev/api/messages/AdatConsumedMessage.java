package org.ssldev.api.messages;

import org.ssldev.api.chunks.Adat;
import org.ssldev.core.messages.Message;

public class AdatConsumedMessage extends Message {

	public Adat adat;

	public AdatConsumedMessage(Adat a) {
		this.adat = a;
	}

}
