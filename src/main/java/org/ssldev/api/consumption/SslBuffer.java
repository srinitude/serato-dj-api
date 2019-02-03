package org.ssldev.api.consumption;

import java.util.LinkedList;
import java.util.Queue;

import org.ssldev.api.utils.BinaryUtil;

public class SslBuffer {
	
	private Queue<Integer> buf = new LinkedList<Integer>();

	public SslBuffer() {
		// empty
	}
	public SslBuffer(Queue<Integer> ret) {
		buf.addAll(ret);
	}
	public boolean isEmpty() {return buf.isEmpty();}
	public int size() {return buf.size();}
	public int poll() {return BinaryUtil.readOneByte(buf);}
	
	public int readInt4() {
		return BinaryUtil.readInt(buf, 4);
	}
	public long readLong8() {
		return BinaryUtil.readLong(buf);
	}
	public String readRawString(int len) {
		return BinaryUtil.readRawString(buf, len);
	}
	
	public SslBuffer remove(int length) {
		if(length > buf.size()) throw new IllegalArgumentException("cannot remove "+length+". buffer size is only " +buf.size());
		
		Queue<Integer> ret = new LinkedList<>();
		while(length-- > 0) ret.add(BinaryUtil.readOneByte(buf)); 
		return new SslBuffer(ret);
	}
	public String readString(int length) {
		return BinaryUtil.readString(buf, length);
	}
	public void add(int b) {
		buf.add(b);
	}
	public void fastforward(int len) {
		BinaryUtil.fastforward(buf, len);
	}
	
	@Override
	public String toString() {
		return buf.size() + "\n" +buf.toString();
	}
	public SslBuffer copy() {
		return new SslBuffer(new LinkedList<Integer>(buf));
	}
	
	/**
	 * @return underlying live buffer. 
	 */
	public Queue<Integer> getBytes() {
		return buf;
	}
}
