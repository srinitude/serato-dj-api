package org.ssldev.api.chunks;
//package chunks;
//
//import java.util.ArrayList;
//import java.util.Queue;
//
//import app.ByteConsumerIF;
//import app.ConsumeStrategy;
//import fields.Field;
//import messages.ChunkReadMessage;
//import messages.ByteConsumerRegistrationMessage;
//import mgmt.EventHub;
//import utils.Logger;
//
///**
// * a Chunk.
// * @author Elad
// *
// */
//public abstract class Unk implements ByteConsumerIF {
//	/** id of this chunk (e.g. 'adat') */
//	String chunkId;
//	int length;
//	
//	ArrayList<Field> fields = new ArrayList<Field>();
//	ConsumeStrategy consumeLogic = null;
//		
//	public Unk(String id) {
//		this.chunkId = id;
//		
//		/** add all fields that make up this chunk*/
//		constructChunk();
//	}
//	
//	public void constructChunk() {
//		// default is empty.  should be overriden if class needs
//		// to construct itself in a meaningful way
//	}
//	
//	public abstract Unk getInstance();
//	
//	public void consume(Queue<Integer> buf) {
//		Logger.debug(this, "consuming <" + chunkId + ">.  bytes remaining: " + buf.size());
//		consumeLogic.consume(buf);
//	}
//	
//	public void registerChunk() {
//		hub.add(new ByteConsumerRegistrationMessage(this));
//	}
//
//	public String getId() {
//		return chunkId;
//	}
//	
//	public String getName() {
//		return chunkId;
//	}
//	
//	public void setData(Object o) {
//		
//	}
//	
//	void publishChunkEvent() {
//		hub.add(new ChunkReadMessage(this));
//	}
//	
//	//TODO: set via constructor instead
//	public void setConsumingStrategy(ConsumeStrategy s) {
//		consumeLogic = s;
//	}
//
//	protected static final String TAB = "   ";
//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder(chunkId); sb.append("\n");
//		
//		sb.append(TAB).append("byte Length ").append(length).append("\n");
//		fields.forEach(f -> {if(f.getVal() != null) sb.append(TAB).append(f).append("\n");});
//		return sb.toString();
//	}
//}
