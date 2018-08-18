package org.ssldev.api.services;

import java.util.List;
import java.util.stream.Collectors;

import org.ssldev.api.chunks.Otrk;
import org.ssldev.api.chunks.Ovct;
import org.ssldev.api.chunks.Ptrk;
import org.ssldev.api.chunks.Tvcn;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.SslByteConsumer;
import org.ssldev.api.messages.BytesConsumedMessage;
import org.ssldev.api.messages.CrateConsumedMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.Service;
import org.ssldev.core.utils.Logger;

/**
 * publishes all {@link Ptrk} consumed as part of a crate.  Each Ptrk represents a track 
 * in a crate.  {@link PtrksConsumedMessage}} <p> 
 * 
 */
public class CrateDataConsumedPublisherService extends Service{

	/**
	 * @param hub
	 */
	public CrateDataConsumedPublisherService(EventHub hub) {
		super(hub);
	}

	@Override
	public void notify(MessageIF msg) {
		if(!(msg instanceof BytesConsumedMessage)) return;
		
		BytesConsumedMessage cMsg = (BytesConsumedMessage) msg;
		
		process(cMsg.consumer, cMsg.fileName);
	}

	private void process(ByteConsumerIF sslData, String crateName) {
		/*
		 * currently structure is as follows:
		 * [1] crate consumer
		 * -->[1] vrsn
		 * -->[1] osrt
		 * 		-->[1] tvcn   // sort-by column name
		 * 		-->[1] brev	  // is descending
		 * -->[*] ovct
		 * 		-->[1] tvcn   // column name
		 * 		-->[1] tvcw   // column width?
		 */
		if(!(sslData instanceof SslByteConsumer)) {
			throw new IllegalStateException("structure may have changed. assumed top level consumer is SslByteConsumer. instead got "+sslData.getClass().getSimpleName());
		}
		SslByteConsumer ssl = (SslByteConsumer)sslData;
		List<Tvcn> displayedColumns = ssl.getData().stream()
				.filter(Ovct.class::isInstance).map(Ovct.class::cast)
				.map(Ovct::getData)
				.flatMap(ovct -> ovct.stream())
				.filter(Tvcn.class::isInstance).map(Tvcn.class::cast)
				.collect(Collectors.toList());
		
		List<Ptrk> ptrks = ssl.getData().stream()
				.filter(Otrk.class::isInstance).map(Otrk.class::cast)
				.map(Otrk::getData)
				.flatMap(ptrk -> ptrk.stream())
				.filter(Ptrk.class::isInstance).map(Ptrk.class::cast)
				.collect(Collectors.toList());
		
		Logger.debug(this, 
				     crateName + System.lineSeparator() 
				     + " contained display columns:"+System.lineSeparator()
				     + "  " + displayedColumns+System.lineSeparator()
				     +" contained "+ptrks.size()+" tracks:"+System.lineSeparator()
				     + "  " + ptrks);
		
		hub.add(new CrateConsumedMessage(crateName, displayedColumns, ptrks));
	}
	
	public static void main(String[] args) {
		String[] out = {
				"Dobie Gray - Drift Away (Short 95)",
						"Shwayze - Get You Home (short) - Phase Edit", 
						"Far East Movement - Like a G6 (Elad Bass Intro AO)aaaaaaaaaaaaaaaaaaaaaasdasdsfsfsfsafdsfsafsa", 
						"Sak Noel - Loca People (wtf)(dirty)(johnny)"
		};
		
		int i = 0;
		for(String s : out) {
			System.out.println(String.format("%2d. %-20s", i++, s));
		}
		
//		System.out.printf("%2d. %-20s $%.2f%n",  i + 1, "ELAD", 4.00);
	}
	

}
