package org.ssldev.api.messages;

import java.util.Queue;

import org.ssldev.core.messages.Message;

/**
 * an object with ID is ready to be consumed
 */
public class IdToConsumeMessage extends Message {

	public Queue<Integer> buf;
	public String id;

	public IdToConsumeMessage(String id, Queue<Integer> buffer) {
		this.id = id;
		this.buf = buffer;
	}
	
	@Override
	public String toString() {
		return super.toString() + ": " + id + " buf size " + buf.size();
	}

}
