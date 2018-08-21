package org.ssldev.api.messages;

import java.io.File;

import org.ssldev.core.messages.Message;
import org.ssldev.core.utils.Validate;
/**
 * represents a request to convert a serato crate into a pojo
 */
public class CrateConvertRequestMessage extends Message {

	final public File seratoCrateFile;

	/**
	 * create a request to convert the given serato crate file 
	 * 
	 * @param seratoCrateFile must exist
	 */
	public CrateConvertRequestMessage(File seratoCrateFile) {
		Validate.notNull(seratoCrateFile, "crate file cannot be null");
		Validate.isTrue(seratoCrateFile.exists(), "crate file does not exist");
		
		this.seratoCrateFile = seratoCrateFile;
	}
	
	@Override
	public String toString() {
		return getName() + ": " + seratoCrateFile;
	}
}
