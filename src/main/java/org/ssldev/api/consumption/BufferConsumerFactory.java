package org.ssldev.api.consumption;

import java.util.HashMap;

import org.ssldev.core.utils.Logger;

public class BufferConsumerFactory {
	public static final int BUFFER_KEY_MAX_SIZE = 4;
	private HashMap<String,ByteConsumerIF> register = new HashMap<>();
	
	public void register(String id, ByteConsumerIF consumer) {
		if(register.containsKey(id)) {
			Logger.error(this, id + " is already taken by " + register.get(id));
			throw new IllegalArgumentException("ID already taken");
		}
		register.put(id, consumer);
	}
	
	public boolean isRegistered(BufferConsumerKey key) {
		return key.hasId() && 
				(register.containsKey(key.getKeyAsString()) || register.containsKey(key.getKeyAsInteger()));
	}
	
	public boolean isRegistered(IntString key) {
		return (register.containsKey(key.getIntVal()) || register.containsKey(key.getStringVal()));
	}

	public ByteConsumerIF build(BufferConsumerKey key) {
		ByteConsumerIF ret = register.get(key.getKeyAsString());
		if(ret == null) {
			ret = register.get(key.getKeyAsInteger());
			if(ret == null) {
				throw new IllegalStateException("getConsumer called while key was not found: key = " + key);
			}
		}
		
		return ret.getInstance(); 
	}
	public ByteConsumerIF build(IntString key) {
		ByteConsumerIF ic = register.get(key.getIntVal());
		if(null == ic) ic = register.get(key.getStringVal());
		if(null == ic) Logger.error(this, "could not find consumer registered under " + key);
		return ic.getInstance();
	}
	
	public ByteConsumerIF build(String key) {
		return register.get(key).getInstance();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()); 
		sb.append("\n"); sb.append(register);
		return sb.toString();
	}

}
