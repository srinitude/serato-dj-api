package org.ssldev.api.services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.ssldev.api.chunks.Adat;
import org.ssldev.api.chunks.Oent;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.SslByteConsumer;
import org.ssldev.api.messages.AdatConsumedMessage;
import org.ssldev.api.messages.BytesConsumedMessage;
import org.ssldev.api.messages.MultipleAdatsConsumedMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.Service;
import org.ssldev.core.utils.Logger;
/**
 * publishes ADAT chunks that were consumed.  Each Adat represents a track that
 * was played or cued.  {@link MultipleAdatsConsumedMessage}} gets published
 * if multiple ADATs were consumed (generally happens when the App 
 * is started after SSL, and reads in an already populated history session file)
 * 
 * <p>
 * All chunks consumed are also logged out to given file path.
 */
public class DataConsumedPublisherService extends Service {
	
	/**
	 * @param hub
	 */
	public DataConsumedPublisherService(EventHub hub) {
		super(hub);
	}

	@Override
	public void notify(MessageIF msg) {
		if(!(msg instanceof BytesConsumedMessage)) return;
		
		BytesConsumedMessage cMsg = (BytesConsumedMessage) msg;
		
		process(cMsg.consumer);
	}

	private void process(ByteConsumerIF sslData) {
		/*
		 * currently structure is as follows:
		 * [1]ssl
		 * -->[1] vrsn
		 * -->[*] oent
		 * 			-->[*] adat
		 * 						--> [*] field 
		 */
		if(!(sslData instanceof SslByteConsumer)) {
			throw new IllegalStateException("structure may have changed. assumed top level consumer is SslByteConsumer. instead got "+sslData.getClass().getSimpleName());
		}
		SslByteConsumer ssl = (SslByteConsumer)sslData;
		
		List<Oent> oents = new LinkedList<>();
		for(ByteConsumerIF c : ssl.getData()) {
			if(c instanceof Oent) {
				oents.add((Oent) c);
			}
		}
		
		// determine if this is a one track or multi-track update
		if(oents.size() == 1) {
			Logger.debug(this,"one oent updated");
			addAdats(oents.remove(0));
		}
		else if(oents.size() > 1){
			// multi-track happens when reading in a pre-existing history file
			Logger.debug(this,oents.size() +" oents got updated");
			List<Adat> adats = 
					oents.stream().
					map(o-> o.getData()).flatMap(Collection::stream).
					filter(Adat.class::isInstance).
					map(Adat.class::cast).collect(Collectors.toList());
			
			Logger.debug(this,"collected "+adats.size() + " adats");
			hub.add(new MultipleAdatsConsumedMessage(adats));
		}
		else {
			Logger.error(this, "no oents were consumed. structure must be bad");
		}
	}
	
	private void addAdats(Oent c) {
		c.getData().stream().
			filter(b-> b instanceof Adat).
			forEach(adat -> hub.add(new AdatConsumedMessage((Adat) adat)));
	}

}
