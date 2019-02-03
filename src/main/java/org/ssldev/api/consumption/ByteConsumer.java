package org.ssldev.api.consumption;

import java.util.List;

import org.ssldev.api.consumption.strategies.ConsumeStrategy;
import org.ssldev.core.utils.Logger;

public abstract class ByteConsumer implements ByteConsumerIF{
	/** register ID of this consumer. buffer block to consume is assigned by this ID */ 
	protected String id;
	
	/** name of this consumer*/
	protected String name;
	
	/** how the buffer should be consumed */
	protected ConsumeStrategy consume;
	
	/** data consumed from buffer (could be a sub-buffer)*/
	protected Object data;
	
	/** register for any sub-consumers */
	private BufferConsumerFactory register = new BufferConsumerFactory();

	@Override
	public String getId() {
		return null == id? "" : id;
	}

	@Override
	public String getName() {
		return null == name? "" : id;
	}

	public void consume(SslBuffer bytes) {
		Logger.finest(this, " consuming ID <" + id+ ">.  bytes size: " + bytes.size());
		consume.consume(bytes);
		data = consume.getData();
	}
	
	protected void register(ByteConsumerIF b) {
		register.register(b.getId(), b);
	}

	public abstract ByteConsumerIF getInstance();

	public boolean containsConsumer(BufferConsumerKey key) {
		return register.isRegistered(key);
	}
	
	public ByteConsumerIF getConsumer(BufferConsumerKey key) {
		return register.build(key);
	}
	
	public ByteConsumerIF getConsumer(String key) {
		return register.build(key);
	}
	public ByteConsumerIF buildConsumer(IntString key) {
		return register.build(key);
	}
	
	/**
	 * get data that was consumed by the consumers ID
	 * @param _id
	 * @return data consumed by ID or null if consumer not found
	 */
	@SuppressWarnings("unchecked")
	protected ByteConsumerIF getData(String _id) {
		if(data instanceof List<?>) {
			for(ByteConsumerIF b: ((List<ByteConsumerIF>)data)) {
				if(b.getId().equals(_id)) return b;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public boolean isRegistered(IntString key) {
		return register.isRegistered(key);
	}

}
