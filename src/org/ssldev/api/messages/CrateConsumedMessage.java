package org.ssldev.api.messages;

import java.util.List;

import org.ssldev.api.chunks.Ptrk;
import org.ssldev.api.chunks.Tvcn;
import org.ssldev.core.messages.Message;
/**
 * crate name and contents 
 */
public class CrateConsumedMessage extends Message {

	public String crateAbsolutePath;
	public List<Ptrk> ptrks;
	public List<Tvcn> displayedColumns;

	public CrateConsumedMessage(String crateName, List<Tvcn> displayedColumns, List<Ptrk> ptrks) {
		this.crateAbsolutePath = crateName;
		this.displayedColumns = displayedColumns;
		this.ptrks = ptrks;
	}

}
