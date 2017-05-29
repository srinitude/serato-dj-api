package org.ssldev.api.fields;

import org.ssldev.api.consumption.SslBuffer;

public class AdatField39 extends Field <AdatField39 >{

	public AdatField39() {
		super("Field39",39);
	}
	
	@Override
	public void consume(SslBuffer buf) {
		int len = buf.size(); 
		if(len != 1) throw new IllegalStateException("assumed always length of 1. instead length == " +len);
		data = buf.poll();
	}

}
