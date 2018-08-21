package org.ssldev.api.consumption;

import java.nio.ByteBuffer;
import java.util.Queue;

import org.ssldev.core.utils.Logger;

/**
 * string and int representation of byte array
 */
public class IntString {
	private String intval;
	private String stringval;
	
	public IntString(SslBuffer bytes) {
		if(bytes.size() != 4) throw new IllegalArgumentException("bytes must be 4. not " + bytes);
		SslBuffer copy = bytes.copy();
		
		intval = String.valueOf(readInt4(bytes.getBytes()));
		stringval = readString4(copy.getBytes());
	}
	
	
	/*
	 * temp methods to not clutter up debug printouts
	 */
	private String readString4(Queue<Integer> bytes) {
		int i = 4;
		if(i > bytes.size()) {
			Logger.error("BinaryUtil", 
					"asked to readString "+i+" bytes past current buffer size of "+bytes.size());
			throw new IllegalArgumentException("cannot ff buffer by " + i);
		}
		StringBuilder sb = new StringBuilder();
		while(i-- > 0) {
			int b = bytes.poll();
			char c = (char)b;
			if(b!=0)sb.append(c);
		}
		
		return sb.toString();
	}
	private int readInt4(Queue<Integer> bytes) {
		int i = 4;
		if(i > bytes.size()) {
			Logger.error("BinaryUtil", 
					"asked to readInt "+i+" bytes past current buffer size of "+bytes.size());
			throw new IllegalArgumentException("cannot ff buffer by " + i);
		}
		if(i != 4) {
			throw new IllegalArgumentException("can only read 4 ints, not " + i);
		}

		byte[] bs = new byte[i];
		for(int ii=0; ii< i ; ii++) {
			byte b = bytes.poll().byteValue();
			bs[ii] = b;
		}
		ByteBuffer wrap = ByteBuffer.wrap(bs);
		int ans = wrap.getInt();
		return ans;
	}
	/** end temp methods */
	
	public String getStringVal() {
		return stringval;
	}
	public String getIntVal() {
		return intval;
	}
	
	@Override
	public String toString() {
		return "int val[" + intval + "] string val["+stringval+"]";
	}

}
