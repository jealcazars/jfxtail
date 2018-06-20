package com.jealcazars.jfxtail.file;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

public class FileListener implements Runnable {
	private static final Logger LOG = Logger.getLogger(FileListener.class.getName());

	public static final String FILE_WAS_MODIFIED = "File was modified";
	private boolean stop = false;
	private File file;
	private PropertyChangeSupport propertyChangeSupport;
	private long lastLength = 0l;

	public FileListener(File file) {
		this.file = file;
		lastLength = file.length();
		propertyChangeSupport = new PropertyChangeSupport(this);
		new Thread(this).start();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public void run() {
		checkFileModification();
	}

	public synchronized void stop() {
		LOG.fine("Stopping listener " + file.getName());

		stop = true;
	}

	public synchronized void restart() {
		LOG.fine("Restarting listener  " + file.getName());
		stop = false;
		new Thread(this).start();
	}

	private void checkFileModification() {
		while (!stop) {
			try {
				if (file.length() != lastLength) {
					propertyChangeSupport.firePropertyChange(FILE_WAS_MODIFIED, lastLength, file.length());
					lastLength = file.length();
				}

				Thread.sleep(JfxTailAppPreferences.getRefreshRate());
			} catch (Exception e) {
				LOG.log(Level.SEVERE, "Error", e);
			}
		}
		LOG.fine("Finished listener");
	}
}
