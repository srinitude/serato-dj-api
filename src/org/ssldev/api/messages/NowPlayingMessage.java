package org.ssldev.api.messages;

import org.ssldev.api.chunks.Adat;
import org.ssldev.core.messages.MessageIF;

/**
 * message containing last song read in
 */
public class NowPlayingMessage implements MessageIF {
	private String fullpath;
	private String artist;
	private String title;
	private String key;
	private int bpm;
	private long playtime;
	private long starttime;
	private long endtime;
	
	private int deck;
	
	public final Adat adatForm;
	
	public NowPlayingMessage(Adat a, int deck) {
		fullpath = a.getFullPath();
		artist = a.getArtist();
		title = a.getTitle();
		playtime = a.getTotalPlayTime();
		starttime = a.getStartTime();
		endtime = a.getEndTime();
		key = a.getKey();
		bpm = a.getBpm();
		
		adatForm = a;
		
		this.deck = deck;
	}
	
	public String getFullpath() {return fullpath;}
	public String getArtist() {return artist;}
	public String getTitle() {return title;}
	public long getEndtime() {return endtime;}
	public long getPlaytime() {return playtime;}
	public long getStarttime() {return starttime;}
	public String getKey() {return key;}
	public int getDeck() {return deck;}
	public int getBpm() {return bpm;}
	
	@Override
	public String toString() {
		return artist + " - " + title;
	}
}
