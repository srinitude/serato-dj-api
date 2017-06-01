SSLAPI
======

the SSL API parses in the binary history file produced by Serato DJ / ScratchLive
and publishes out play data as events.  It is meant to provide an (unofficial) 
API for any developers looking to create Serato based apps.  

This project was inspired by Ben XO’s SslScrobbler project.  He has some great 
documentation on the SSL binary format and overall chunk structure, so i won’t 
repeat it here.  Check out his project for more details: https://github.com/ben-xo/sslscrobbler


Background
==========
I’ve created the SSL API as a fun side project, to support another App i’m 
working on, and in the hopes of making it easier for other devs to create Serato 
applications.  The API is meant to provide a middleware that takes care of the 
parsing/processing of SSL byte data, and publishes out the data as tracks, to 
interested subscribers.


How to use the API
==================
The recommended way to use SSLAPI is to register the client as a service and specify
which messages of interest the client should receive:

		/*
		 * 1. instantiate the ssl-api
		 */
		SslApi api = new SslApi();
		
		/*
		 * 2. initialize the app (without supplying an event hub)
		 */
		api.init(null);
		
		/*
		 * 3. register a service and provide message types to subscribe to
		 */
		api.register(new ServiceIF() {

			@Override
			public void notify(MessageIF msg) {
				System.out.println("DEMO APP got notified of " + msg.getClass().getSimpleName() + ": "+msg);
			}

			@Override
			public void shutdown() {
				// null
			}

			@Override
			public String getName() {
				return "ssl api demo";
			}
		}	
		// subscribe for mostly single track changes, as modeled by the TrackPublisherService
		, NowPlayingMessage.class, NowInCueMessage.class);


How does it work
================
the API is designed around the EventHub pub/sub pattern, where each (micro)service 
registers with a central event hub, and acts upon the message that it is interested
in.  An async event hub is used (i.e blocking queue) as a simplistic threading 
model for the app.

once initialized, SSLAPI picks out which history session file to monitor (if Serato DJ/SSL 
has started; otherwise, it'll wait for one to be created)(SslCurrentSessionFinderService).
Then on each consecutive update it parses out newly appended bytes into OENT/ADAT
chunks (SslByteConsumerService & DataConsumedPublisherService).

-TBD- Byte consumer structure and consumption strategy descriptions

To provide a more meaningful abstraction, the API models “decks” that publish 
events such as ‘NowPlaying’ or ‘NowInCue’ to show what tracks are playing and on
what deck.  In contrast, raw or non-abstracted data is contained in the AdatConsumedMessage 

NOTE: since SeratoDj/SSL cannot differentiate whether a song is really playing 
or not (controlled by the mixer crossfader/volume knob) neither can this API.  To 
keep things simple, no effort was taken to guess when a track is actually playing 
or not.  At the moment it's left up to client applications to implement the logic 
to do so.  


Disclaimer
==========
SSLAPI is free open source software licensed under the MIT license and is provided 
without warranties of any kind.
