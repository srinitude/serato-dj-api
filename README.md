SSLAPI
======

the SSL API parses in the binary history file produced by Serato DJ / ScratchLive
and publishes out play data as events.  It is meant to act as a middleware and
serve as an (unofficial) API for any developers looking to utilize Serato play data.  

This project was inspired by Ben XO’s SslScrobbler project.  He has some great 
documentation on the SSL binary format and overall chunk structure, so i won’t 
repeat it here.  Check out his project for more details: https://github.com/ben-xo/sslscrobbler

Quick Start guide
-----------------
I created a GUI to demonstrate the type of play data that can be received:
1. clone and build the project in your favorite IDE
2. set `startGui=true` in `resources/SslApiAppConfiguration.properties`
3. run the main method in `org.ssldev.api.app.SslApi`

The GUI can be started before or after SSL/Serato DJ, and should display a track
as soon as it is loaded to one of the decks.  The ssldom.txt file (`/home/<user>/.sslapi/ssldom.txt`)
will capture all tracks played so far in the current session.  Logging is piped 
to `/home/<user>/.sslapi/log.txt`.

How to use the API
------------------
The recommended way to use SSLAPI is to simply register a service client and specify
which messages the client should receive:

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
				// shutdown logic if necessary...
			}

			@Override
			public String getName() {
				return "ssl api demo";
			}
		}	
		// subscribe for mostly single track changes, as modeled by the TrackPublisherService
		, NowPlayingMessage.class, NowInCueMessage.class);


How does it work
----------------
the app framework is designed around the EventHub pub/sub pattern, where each (micro)service 
registers with a central event hub, and acts upon message/events that it is interested
in.  An async event hub is used (i.e blocking queue) as a simplistic threading 
model for the app.

once initialized, SSLAPI picks out which history session file to monitor (if Serato DJ/SSL 
has started; otherwise, it'll wait for one to be created)(`SslCurrentSessionFinderService`).
Then on each consecutive update it parses out newly appended bytes into OENT/ADAT
chunks (`SslByteConsumerService` & `DataConsumedPublisherService`).

Byte Consumers fall into 2 types: compound & leafs.  Compound consumers (e.g OENT, ADAT)
are made up of child byte consumers.  Each byte consumer encapsulates all knowledge
of how to consume binary data and store it.

The intent of the design is to make it easy to add/modify/edit the byte consumers
as the SSL binary format evolves.  For example, the ADAT chunk contains data fields
such as BPM, TITLE, etc.  Adding a new field is as easy as creating a new field
byte consumer specifying what consumption strategy to use, and adding it to the 
ADAT via the `constructAdat()` method.    

Messages/Events
---------------
To provide a more meaningful abstraction, the API models “decks” that publish 
events such as ‘NowPlaying’ or ‘NowInCue’ to show what tracks are playing and on
what deck.  In contrast, non-abstracted data is contained in the AdatConsumedMessage 

NOTE: since SeratoDj/SSL cannot differentiate whether a song is really playing 
or not (controlled by the mixer crossfader/volume knob) neither can this API.  To 
keep things simple, no effort was taken to guess when a track is actually playing 
or not.  At the moment it's left up to client applications to implement the logic 
to do so.  


Disclaimer
----------
SSLAPI is free open source software licensed under the MIT license and is provided 
without warranties of any kind.
