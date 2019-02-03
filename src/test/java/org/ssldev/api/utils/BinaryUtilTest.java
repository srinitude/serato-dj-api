package org.ssldev.api.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;
import org.ssldev.api.utils.BinaryUtil;

public class BinaryUtilTest {
	private Queue<Integer> bytes;
	@Before
	public void setUp() throws Exception {
		bytes = new LinkedList<Integer>();
	}

	@Test
	public void testReadString() {
		String s = "abcd";
		fillQueue(s,bytes);
		String ret = BinaryUtil.readString(bytes, 4);
		assertEquals(s,ret);
	}
	
	@Test
	public void testReadInt() {
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(8));
		int id = BinaryUtil.readInt(bytes, 4);
		assertEquals(8,id);

		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(1));
		bytes.add(Integer.valueOf(36));
		id = BinaryUtil.readInt(bytes, 4);
		assertEquals(292,id);
	}
	@Test
	public void testReadLong() {
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(8));
		long id = BinaryUtil.readLong(bytes);
		assertEquals(8,id);
	}
	@Test
	public void testLength() {
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(5));
		int id = BinaryUtil.readInt(bytes,4);
		assertEquals(5, id);
	}
	@Test
	public void testFastForwardBuffer() {
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(1));
		bytes.add(Integer.valueOf(5));
		BinaryUtil.fastforward(bytes, 1);
		assertEquals(3, bytes.size());
		BinaryUtil.fastforward(bytes, 3);
		assertTrue(bytes.isEmpty());
		try {
			BinaryUtil.fastforward(bytes, 3);
		} catch (IllegalArgumentException e) {
			return;
		}
		fail("should not be able to ff empty buffer");
	}
	
	@Test
	public void testReadTil() {
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(49)); // 1
		bytes.add(Integer.valueOf(0)); 
		bytes.add(Integer.valueOf(50)); // 2
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(0));
		bytes.add(Integer.valueOf(49)); // 1
		bytes.add(Integer.valueOf(50)); // 2
		
		assertEquals("12",BinaryUtil.readTilEmpty(bytes));
		assertEquals(4,bytes.size());
	}
	
	@Test
	public void testByteToDecimal() {
		bytes.add(0); bytes.add(0); bytes.add(0); bytes.add(4);
//		bytes.add(0); bytes.add(0); bytes.add(1); bytes.add(36);
		BinaryUtil.readInt(bytes, bytes.size());
		
		ByteBuffer b = ByteBuffer.allocate(4);
		b.put((byte)0); ; b.put((byte)0); b.put((byte)1); b.put((byte) 36);
		byte[] ba= new byte[4];
		ba[0] = 0;
		ba[1] = 0; ba[2] = 1; ba[3] = 36;
		
		System.out.println(ByteBuffer.wrap(ba).getInt());
	}
	
	private void fillQueue(String s, Queue<Integer> q) {
		for(int i=0;i<s.length();i++) {
			q.add(Integer.valueOf(ctoi(s.charAt(i))));
		}
	}
	
	private int ctoi(char c) {
		return Integer.valueOf(c - 0);
	}
}
