package org.ssldev.api.fields;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.core.utils.Logger;

public abstract class Field<T extends ByteConsumer> extends ByteConsumer {
	private Class<T> clazz;
	
	public Field(String n, int _id) {
		super();
		name = n;
		this.id = String.valueOf(_id); //TODO decide if id be string or int
	}

	// getters
	public String getName() { return name;}
	public String getId() { return id;}
	public Object getData() {return data;}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(name);
		sb.append(": ").append(data);
		return sb.toString();
	}
	
	@Override
	public ByteConsumerIF getInstance() {
		clazz = (Class<T>) this.getClass();
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			Logger.error(this, e.toString());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Logger.error(this, e.toString());
			e.printStackTrace();
		}
		
		throw new RuntimeException("could not instantiate class");
	}

}
