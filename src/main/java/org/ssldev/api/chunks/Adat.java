package org.ssldev.api.chunks;

import java.util.List;

import org.ssldev.api.consumption.ByteConsumer;
import org.ssldev.api.consumption.ByteConsumerIF;
import org.ssldev.api.consumption.strategies.CompoundChunkConsumeStrategy;
import org.ssldev.api.fields.Adat10LengthField;
import org.ssldev.api.fields.Adat11SizeField;
import org.ssldev.api.fields.Adat13BitrateField;
import org.ssldev.api.fields.Adat14FrequencyField;
import org.ssldev.api.fields.Adat15BpmField;
import org.ssldev.api.fields.Adat16Field;
import org.ssldev.api.fields.Adat17CommentField;
import org.ssldev.api.fields.Adat18LanguageField;
import org.ssldev.api.fields.Adat19GroupingField;
import org.ssldev.api.fields.Adat1RowField;
import org.ssldev.api.fields.Adat20Remixer;
import org.ssldev.api.fields.Adat21LabelField;
import org.ssldev.api.fields.Adat22ComposerField;
import org.ssldev.api.fields.Adat23YearField;
import org.ssldev.api.fields.Adat28StartTimeField;
import org.ssldev.api.fields.Adat29EndTimeField;
import org.ssldev.api.fields.Adat2FullPathField;
import org.ssldev.api.fields.Adat31DeckField;
import org.ssldev.api.fields.Adat3LocationField;
import org.ssldev.api.fields.Adat45PlaytimeField;
import org.ssldev.api.fields.Adat48SessiondField;
import org.ssldev.api.fields.Adat4FileNameField;
import org.ssldev.api.fields.Adat50PlayedField;
import org.ssldev.api.fields.Adat51KeyField;
import org.ssldev.api.fields.Adat52AddedField;
import org.ssldev.api.fields.Adat53UpdatedAtField;
import org.ssldev.api.fields.Adat6TitleField;
import org.ssldev.api.fields.Adat7ArtistField;
import org.ssldev.api.fields.Adat8AlbumField;
import org.ssldev.api.fields.Adat9GenreField;
import org.ssldev.api.fields.AdatField33;
import org.ssldev.api.fields.AdatField39;
import org.ssldev.api.fields.Adat63PlayerTypeField;
import org.ssldev.api.fields.AdatField68;
import org.ssldev.api.fields.AdatField69;
import org.ssldev.api.fields.AdatField70;
import org.ssldev.api.fields.AdatField72;
/**
 * this is a data compound chunk made up of a bunch fields  
 */
public class Adat extends ByteConsumer {
	
	public Adat() {
		super();
		id = name = "adat";
		consume = new CompoundChunkConsumeStrategy(this);
		
		constructAdat();
	}
	
	@Override
	public ByteConsumerIF getInstance() {
		return new Adat();
	}
	public String getFullPath() {
		ByteConsumerIF b = getData(Adat2FullPathField.ID);
		return null == b ? "" : (String) b.getData();
	}
	public String getTitle() {
		ByteConsumerIF b = getData(Adat6TitleField.ID);
		return null == b ? "" : (String) b.getData();
	}
	public String getArtist() {
		ByteConsumerIF b = getData(Adat7ArtistField.ID);
		return null == b ? "" : (String) b.getData();
	}
	public long getStartTime() {
		ByteConsumerIF b = getData(Adat28StartTimeField.ID);
		return null == b ? 0 : parseLong(b.getData());
	}
	public long getEndTime() {
		ByteConsumerIF b = getData(Adat29EndTimeField.ID);
		return null == b ? 0 : parseLong(b.getData());
	}
	public long getTotalPlayTime() {
		ByteConsumerIF b = getData(Adat45PlaytimeField.ID);
		return null == b ? 0 : parseLong(b.getData());
	}
	public String getKey() {
		ByteConsumerIF b = getData(Adat51KeyField.ID);
		return null == b ? "" : (String) b.getData();
	}
	public int getDeck() {
		ByteConsumerIF b = getData(Adat31DeckField.ID);
		return null == b ? 0 : (int) b.getData();
	}
	public int getRow() {
		ByteConsumerIF b = getData(Adat1RowField.ID);
		return null == b ? 0 : (int) b.getData();
	}
	public boolean isPlayed() {
		ByteConsumerIF b = getData(Adat50PlayedField.ID);
		return null != b && (int)b.getData() != 0;
	}
	public long getUpdateTime() {
		ByteConsumerIF b = getData(Adat53UpdatedAtField.ID);
		return null == b ? 0 : parseLong(b.getData());
	}
	public int getBpm() {
		ByteConsumerIF b = getData(Adat15BpmField.ID);
		return null == b ? 0 : (int) b.getData();
	}

	private void constructAdat() {
		// register all the fields
		register(new Adat1RowField()); 		 	// 1
		register(new Adat2FullPathField()); 	// 2
		register(new Adat3LocationField()); 	// 3
		register(new Adat4FileNameField()); 	// 4 
		register(new Adat6TitleField());	 	// 6
		register(new Adat7ArtistField());	 	// 7 
		register(new Adat8AlbumField());	 	// 8 
		register(new Adat9GenreField());	 	// 9 
		register(new Adat10LengthField());		// 10 
		register(new Adat11SizeField());	 	// 11 
		register(new Adat13BitrateField());		// 13 
		register(new Adat14FrequencyField());	// 14
		register(new Adat15BpmField());		 	// 15
		register(new Adat16Field());		 	// 16
		register(new Adat17CommentField());		// 17
		register(new Adat18LanguageField());	// 18
		register(new Adat19GroupingField());	// 19
		register(new Adat20Remixer());			// 20 
		register(new Adat21LabelField());		// 21
		register(new Adat22ComposerField());	// 22
		register(new Adat23YearField());		// 23
		register(new Adat28StartTimeField());	// 28
		register(new Adat29EndTimeField());		// 29
		register(new Adat31DeckField());		// 31
		register(new AdatField33()); 			// 33 
		register(new AdatField39()); 			// 39 
		register(new Adat45PlaytimeField()); 	// 45 
		register(new Adat48SessiondField()); 	// 48 
		register(new Adat50PlayedField()); 		// 50 
		register(new Adat51KeyField()); 		// 51 
		register(new Adat52AddedField()); 		// 52 
		register(new Adat53UpdatedAtField()); 	// 53 
		register(new Adat63PlayerTypeField()); 	// 63 
		register(new AdatField68()); 			// 68 
		register(new AdatField69()); 			// 69 
		register(new AdatField70()); 			// 70 
		register(new AdatField72()); 			// 72 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ByteConsumerIF> getData() {
		return (List<ByteConsumerIF>) data;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append("\n"); 
		for(ByteConsumerIF b : getData()) {
			sb.append("\t\t").append(b).append("\n");
		}
		return sb.toString();
	}
	
	
	private static long parseLong(Object obj) {
		if(obj instanceof Number) {
			return Long.parseLong(String.valueOf(obj));
		}
		throw new IllegalArgumentException("'" +obj + "' is not a number");
	}
	

}
