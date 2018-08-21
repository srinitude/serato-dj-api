package org.ssldev.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Queue;

import org.ssldev.core.utils.Logger;

/**
 * utilty to extract ascii from binary data
 */
public class BinaryUtil {
	/** index of byte read in*/
	private static int index;
	
	public static String read(FileInputStream fis, int i) throws IOException {
		StringBuilder sb = new StringBuilder();
		while(i-- > 0) {
			int b = fis.read();
			char c = toChar(b);
			debug(index++ + ": " + " " + c + "["+b+"]");
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * reads a string of size i from the buffer.  does NOT append '0's. 
	 * @param bytes
	 * @param i
	 * @return
	 */
	public static String readString(Queue<Integer> bytes, int i) {
		if(i > bytes.size()) {
			Logger.error("BinaryUtil", 
					"asked to readString "+i+" bytes past current buffer size of "+bytes.size());
			throw new IllegalArgumentException("cannot ff buffer by " + i);
		}
		StringBuilder sb = new StringBuilder();
		while(i-- > 0) {
			int b = bytes.poll();
			char c = toChar(b);
			debug(index++ + ": " + " " + c + "["+b+"]");
			if(b!=0)sb.append(c);
		}
		
		return sb.toString();
	}

	/**
	 * reads a string of size i from buffer.  appends '0''s to the string.  should not be used
	 * for field like string values 
	 * @param bytes
	 * @param i
	 * @return
	 */
	public static String readRawString(Queue<Integer> bytes, int i) {
		if(i > bytes.size()) {
			Logger.error("BinaryUtil", 
					"asked to read "+i+" bytes past current buffer size of "+bytes.size());
			throw new IllegalArgumentException("cannot ff buffer by " + i);
		}
		
		StringBuilder sb = new StringBuilder();
		while(i-- > 0) {
			int b = bytes.poll();
			char c = toChar(b);
			debug(index++ + ": " + " " + c + "["+b+"]");
			if(b==0)sb.append(0);
			else sb.append(c);
		}
		return sb.toString();
	}

	public static int readInt(Queue<Integer> bytes, int i) {
		if(i > bytes.size()) {
			Logger.error("BinaryUtil", 
					"asked to readInt "+i+" bytes past current buffer size of "+bytes.size());
			throw new IllegalArgumentException("cannot ff buffer by " + i);
		}
		if(i != 4) {
			//TODO refactor method and take away i parameter
			throw new IllegalArgumentException("can only read 4 ints, not " + i);
		}

		byte[] bs = new byte[i];
		for(int ii=0; ii< i ; ii++) {
			byte b = bytes.poll().byteValue();
			bs[ii] = b;
			debug(index++ + ": " + " " + toChar(bs[ii]) + "["+bs[ii]+"]");
		}
		ByteBuffer wrap = ByteBuffer.wrap(bs);
		int ans = wrap.getInt();
		return ans;
	}

	public static void fastforward(Queue<Integer> buf, int i) {
		if(i > buf.size()) {
			Logger.error("BinaryUtil", 
					"asked to fastforward the buffer "+i+" past its current size of "+buf.size());
			throw new IllegalArgumentException("cannot ff buffer by " + i);
		}
		debug("fastforwarding the buffer by " +i+":");
		while(i-- >0) { 
			int b = buf.poll();
			char c = toChar(b);
			debug(index++ + ": " + " " + c + "["+b+"]");
		}
	}

	private static void debug(String s) {
		Logger.finest(BinaryUtil.class, s);
	}

	private static char toChar(int c) {
		return (char)c;
	}

	/**
	 * reads a string til encountering an empty string ("0000")
	 * @param buf
	 * @return
	 */
	public static String readTilEmpty(Queue<Integer> buf) {
		StringBuilder ret = new StringBuilder();
		String b;
		do {
			b = readString(buf,4);
			ret.append(b);
			
		} while(!b.isEmpty());
		
		return ret.toString();
	}

	private static String peekString(Queue<Integer> buf, int i) {
		if(i > buf.size()) throw new IllegalArgumentException("cannot peek string of length " +i+" when buffer is size " +buf.size());
		
		StringBuilder sb = new StringBuilder(); Iterator<Integer> itr = buf.iterator();
		while(i-- > 0 && itr.hasNext()) {
			sb.append(itr.next());
		}
		return sb.toString();
	}

	private static void copy(Queue<Integer> from, Queue<Integer> to, int numbytes) {
		Iterator<Integer> itr = from.iterator();
		while(numbytes-- > 0) {
			if(itr.hasNext()) to.add(itr.next());
		}
	}

	private static void move(Queue<Integer> from, Queue<Integer> to, int numbytes) {
		while(numbytes-- > 0) {
			to.add(from.poll());
		}
	}

	public static int readOneByte(Queue<Integer> bytes) {
		if(bytes.isEmpty()) throw new IllegalArgumentException("buffer is empty");
		int b = bytes.poll();
		debug(index++ + ": " + " " + toChar(b) + "["+b+"]");
		return b;
	}

	public static int readIntLength1(Queue<Integer> buf) {
		return buf.poll();
	}

}
