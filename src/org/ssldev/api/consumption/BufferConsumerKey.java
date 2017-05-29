package org.ssldev.api.consumption;

/**
 * a key for a buffer/byte consumer to register with.  
 * TODO: inefficient.  should be refactored 
 */
public class BufferConsumerKey {
	public final static int MAX_KEY_SIZE = 4;
	private StringBuilder intId = new StringBuilder();
	private StringBuilder strId = new StringBuilder();
	
	/*
	 * '50,118,4,72'
	 * '0,0,1,0'
	 * '0,0,0,10'
	 */
	public void appendToKey(int b) {
		if(b != 0) intId.append(b);
		strId.append((char)b);
	}
	
	public void clear() {
		intId.setLength(0);
		strId.setLength(0);
	}

	public boolean hasId() {
		return intId.length() > 0 && strId.length() > 0;
	}

	public String getKeyAsString() {
		// up to 4 chars
		if(strId.length() > MAX_KEY_SIZE) throw new IllegalStateException("key '"+strId.toString()+"' is greater than "+MAX_KEY_SIZE);
		return strId.toString();
	}
	public String getKeyAsInteger() {
		// int id may store ascii values for char thus could be greater than 4
		return intId.toString();
	}
	
	@Override
	public String toString() {
		return "int id: " + intId.toString() + ", str id: " + strId.toString();
	}

	public int getLength() {
		return strId.length();
	}
	public void setStringKey(String key) {
		// overwrite the current keys
		clear();
		strId.append(key); intId.append(key);
	}
	
}
