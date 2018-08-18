package org.ssldev.api.messages;

import java.util.List;
import java.util.Objects;

import org.ssldev.core.messages.Message;
/**
 * serato crate contents after conversion
 */
public class CrateConvertResponseMessage extends Message {

	public String crateName;
	public List<String> displayedColumns;
	public List<String> crateTracksPaths;

	public CrateConvertResponseMessage(String crateName, List<String> displayedColumns, List<String> crateTracksPaths) {
		Objects.requireNonNull(displayedColumns, "displayed columns cannot be null");
		Objects.requireNonNull(crateTracksPaths, "crate track paths cannot be null");
		Objects.requireNonNull(crateName, "crate name cannot be null");
		
		this.crateName = crateName;
		this.displayedColumns = displayedColumns;
		this.crateTracksPaths = crateTracksPaths;
	}

}
