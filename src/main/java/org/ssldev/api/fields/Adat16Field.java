package org.ssldev.api.fields;

import java.util.ArrayList;

import org.ssldev.api.consumption.SslBuffer;

public class Adat16Field extends Field <Adat16Field >{

	public Adat16Field() {
		super("field16",16);
	}
	
	@Override
	public void consume(SslBuffer buf) {
		int len = buf.size(); 
		if(len != 8) throw new IllegalStateException("made assumption that field 16 will always have length 8. instead got " +len);
		ArrayList<Integer> val = new ArrayList<>();
		val.add(buf.readInt4()); val.add(buf.readInt4());
		data = val; 
	}
}
