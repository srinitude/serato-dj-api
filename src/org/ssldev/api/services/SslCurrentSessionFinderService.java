package org.ssldev.api.services;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.ssldev.api.messages.SslSessionFileAddedOrModifiedMessage;
import org.ssldev.core.messages.MessageIF;
import org.ssldev.core.mgmt.EventHub;
import org.ssldev.core.services.Service;
import org.ssldev.core.utils.Logger;
/**
 * searches for today's most current session file.  will block until 
 * either a file is modified or is created.  once a file is found this service
 * exists.
 */
public class SslCurrentSessionFinderService extends Service{

	private static final String HISTORY_LOC = "/History/Sessions";
	private static final String SESSION_POSTFIX = ".session";
	/** signifies service should shutdown */
	private boolean isShutdown = false;
	
	private static Modifier HIGH_SENSITIVITY = null;
	static {
		/*
		 * watcher service is slow for OSX.  attempting to speed up by accessing a private modifier
		 * offered by Sun.  This modifier is NOT offered as an API and therefore may break the code at any time... 
		 */
		try {
			Class<?> c = Class.forName("com.sun.nio.file.SensitivityWatchEventModifier");
			Field    f = c.getField("HIGH");
			HIGH_SENSITIVITY = (Modifier) f.get(c);
		} catch (Exception e) {
			Logger.warn(SslCurrentSessionFinderService.class, "could not access the Sun High Sensitivity Modifier. "
					+ "File change notification times may be slow");
		}
	}

	public SslCurrentSessionFinderService(EventHub hub, String pathToSeratoFolder) {
		super(hub);
		searchForLatestSession(pathToSeratoFolder);
	}
	
	private void searchForLatestSession(String path) {
		File sslHomeDir = new File(path);
		if(!sslHomeDir.exists() || !sslHomeDir.isDirectory()) throw new IllegalArgumentException("bad ssl dir given " + path);
		File sessionDir = new File(path+HISTORY_LOC);
		if(!sessionDir.exists() || !sessionDir.isDirectory()) throw new IllegalArgumentException("could not find the sessions dir" + path+HISTORY_LOC);

		// listen for an updating session file
		hub.invokeLater(()->activateWatchService(sessionDir));
	}

	/**
	 * listens for session files added or modified in given dir.  this
	 * method will block until a file is found.
	 * @param sessionDir
	 */
	private void activateWatchService(File sessDir) {
		try (WatchService watcher = FileSystems.getDefault().newWatchService()){
			Path dir = sessDir.toPath();
//			
			if(null == HIGH_SENSITIVITY) {
				dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
			}
			else {
				dir.register(watcher, new WatchEvent.Kind[]{StandardWatchEventKinds.ENTRY_MODIFY}, HIGH_SENSITIVITY);
			}

			Logger.info(this, "registered as a change listener in: " + sessDir.getAbsolutePath());
			while(!isShutdown ) {

				WatchKey key;
				try {
					Logger.finest(this, Thread.currentThread().getName() + " blocked to listen for file events in "+sessDir.getAbsolutePath()+"...");
					key = watcher.take(); // blocking call
				} catch (InterruptedException ex) {
					return;
				}

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();

					if (kind == ENTRY_MODIFY &&
							fileName.toString().endsWith(SESSION_POSTFIX)) {
						Logger.debug(this, "detected file "+ fileName.getFileName()+" was MODIFIED");
						publish(dir.resolve(fileName));
						break;
					}
					else if(kind == ENTRY_CREATE && fileName.toString().endsWith(SESSION_POSTFIX)) {
						Logger.debug(this, "detected file "+ fileName.getFileName()+" was ADDED");
						publish(dir.resolve(fileName));
						break;
					} else {
						Logger.debug(this,"detected file "+fileName.getFileName()+" was "+kind.name());
					}
				}

				boolean valid = key.reset();
				if (!valid) {
					Logger.warn(this, "watcher service returned invalid key");
				}

			}
		} catch (IOException ex) {
			Logger.error(this, ex.toString());
		}
	}

	private void publish(Path f) {
		hub.add(new SslSessionFileAddedOrModifiedMessage(f.toAbsolutePath().toString()));
	}

	@Override
	public void notify(MessageIF msg) {
		// TODO currently sets up listening on startup.. could refactor to listen on event instead
	}
	@Override
	public void shutdown() {
		super.shutdown();
		isShutdown = true;
	}

}
