package org.ssldev.api.consumption;

/**
 * an object that knows how to consume bytes 
 */
public interface ByteConsumerIF {

	Object data = null;

	public String getId();

	public String getName();

	public void consume(SslBuffer bytes);

	public ByteConsumerIF getInstance();

	default public Object getData() {
		return data;
	};

}
