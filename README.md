SSLAPI
======

the SSL API parses in the binary history file produced by Serato DJ / ScratchLive
and publishes out play data as events.  It is meant to act as a middleware and
serve as an (unofficial) API for any developers looking to utilize Serato play data.  

This project was inspired by Ben XO’s SslScrobbler project.  He has some great 
documentation on the SSL binary format and overall chunk structure, so i won’t 
repeat it here.  Check out his project for more details: https://github.com/ben-xo/sslscrobbler

contact me via __http://projects.ssldev.org__ with any comments/questions.


Dev Notes:
----------
**08/23/2018 (v0.2):** Mavenized the project and uploaded v0.2 artifact to Maven central. SSL-API can 
now easily be used by other Maven projects, by adding the following dependency:
```xml
  <dependency>
   <groupId>org.ssldev</groupId>
   <artifactId>SSL-API</artifactId>
   <version>0.2</version>
   <scope>compile</scope>
  </dependency>
```

**07/31/2018:** Added ability to convert a serato crate file via the `CrateConvertRequestMessage`

**04/29/2018:** Added additional ADAT fields introduced by Serato DJ pro (field's 63, 68, 69). 
Also noticed that field 72 appears when playing tracks in offline mode. (ps: If anyone 
knows what their purpose is, please drop me a note!)

**06/10/2017:** added an executable version of the app that launches the GUI demo.  Just download
the SslGuiDemo.jar and launch it (works on macosx). Requires JRE8 (JavaRunTime) or better.  
Can be downloaded here:
http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

Working in Eclipse (with JDK 8 or better)
-----------------------------------------
You'll need to download the latest e(fx)clipse plugin from the eclipse market place,
to avoid JAVAFX compilation warnings.

GUI Quick Start
-----------------
I created a GUI to demonstrate the type of play data that can be received:
1. clone and build the project in your favorite IDE
2. set `startGui=true` in `resources/SslApiAppConfiguration.properties`
3. run the main method in `org.ssldev.api.app.SslApi`

The GUI can be started before or after SSL/Serato DJ, and should display a track
as soon as it is loaded to one of the decks.  The ssldom.txt file (`/home/<user>/.sslapi/ssldom.txt`)
will capture all tracks played so far in the current session.  Logging is piped 
to `/home/<user>/.sslapi/log.txt`.

Maven Build/Run Quick Start
---------------------
open terminal/shell:
1. clone the repo:   
   * git clone git@gitlab.com:eladmaz/SSL-API.git
2. build the project:   
   * cd SSL-API/
   * mvn clean install
3. run the API to receive real-time play events from Serato DJ (note: you can adjust 
the run configuration by modifying `.../ssl-api/src/main/resources/sslApiConfiguration.properties`   
   * mvn exec:java

How to use the API
------------------
The recommended way to use SSLAPI is to simply register a service client and specify
which messages the client should receive:

```java
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
```

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
