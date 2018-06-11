package com.jealcazars.jfxtail.file;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileListener implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(FileListener.class);

	public static final String FILE_WAS_MODIFIED = "File was modified";
	private boolean stop = false;
	private WatchService service;
	private File file;
	private PropertyChangeSupport propertyChangeSupport;
	private long lastLength = 0l;

	public FileListener(File file) throws IOException {
		this.file = file;
		lastLength = file.length();
		propertyChangeSupport = new PropertyChangeSupport(this);
		initializeWatchService();
		new Thread(this).start();
	}

	private void initializeWatchService() throws IOException {
		Path path = Paths.get(file.getParentFile().toURI());
		service = path.getFileSystem().newWatchService();
		path.register(service, ENTRY_CREATE, ENTRY_MODIFY);
		LOG.debug("setWatcherService: {}", file.getAbsolutePath());
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void run() {
		watchFileMofification();
	}

	public synchronized void stop() {
		LOG.debug("Stopping listener {}", file.getName());

		stop = true;
		try {
			service.close();
		} catch (IOException e) {
		}
	}

	public synchronized void restart() {
		LOG.debug("Restarting listener {}", file.getName());

		try {
			initializeWatchService();
			stop = false;
			new Thread(this).start();
		} catch (IOException e) {
			LOG.error("Error", e);
		}
	}

	private void watchFileMofification() {
		WatchKey key = null;

		while (!stop) {
			try {
				key = service.take();
				Kind<?> kind = null;
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					kind = watchEvent.kind();
					if (ENTRY_MODIFY == kind) {
						@SuppressWarnings("unchecked")
						Path modifiedPath = ((WatchEvent<Path>) watchEvent).context();
						if (modifiedPath.endsWith(file.getName())) {
							propertyChangeSupport.firePropertyChange(FILE_WAS_MODIFIED, lastLength, file.length());
							lastLength = file.length();
						}
					}
				}

				if (!key.reset()) {
					break;
				}
			} catch (ClosedWatchServiceException e) {
				LOG.debug("Closed Watch Service");
			} catch (Exception e) {
				LOG.error("Error", e);
			}
		}
		LOG.debug("Finished listener");
	}
}
