package org.ssldev.api.messages;

import java.util.List;

import org.ssldev.api.chunks.Adat;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.utils.Validate;

/**
 * multiple adats were consumed and were added to the DOM as 
 * one unit of work.  Generally only happens once when starting up after 
 * SSL, and SSL has accumulated history.
 */
public class MultipleAdatsConsumedMessage implements MessageIF {

	final public List<Adat> adats;

	public MultipleAdatsConsumedMessage(List<Adat> adats) {
		Validate.notNull(adats, "adats cannot be null");
		Validate.isTrue(adats.size() > 1, "a multi adats added message must contain more than 1 adat.");
		this.adats = adats;
	}

}
