package com.jealcazars.jfxtail.control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.file.FileListener;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class TabFile extends Tab implements PropertyChangeListener {
	private static final Logger LOG = Logger.getLogger(TabFile.class.getName());

	File file;
	FileListener fileListener;
	TextArea textArea = new TextArea();
	private PropertyChangeSupport propertyChangeSupport;

	public TabFile(File file, Scene scene) {
		super();
		propertyChangeSupport = new PropertyChangeSupport(this);

		this.file = file;

		this.setText(file.getName());
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(textArea);
		setContent(scrollPane);

		scrollPane.prefHeightProperty().bind(scene.heightProperty());
		scrollPane.prefWidthProperty().bind(scene.widthProperty());
		textArea.prefWidthProperty().bind(scrollPane.widthProperty());
		textArea.prefHeightProperty().bind(scrollPane.heightProperty());

		loadFile();

		fileListener = new FileListener(file);
		fileListener.addPropertyChangeListener(this);

		setOnClosed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				LOG.fine("Closing tab!");
				fileListener.stop();
			}
		});
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void clear() {
		textArea.setText("");
	}

	public void reload() {
		loadFile();
	}

	public void stop() {
		fileListener.stop();
	}

	public void restart() {
		fileListener.restart();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (FileListener.FILE_WAS_MODIFIED.equals(evt.getPropertyName())) {
			long lastLength = (Long) evt.getOldValue();
			long newLength = (Long) evt.getNewValue();
			readFile((int) lastLength, (int) newLength, true);
		}
	}

	private synchronized void loadFile() {
		readFile(0, (int) file.length(), false);
	}

	private synchronized void readFile(int oldLength, int newLength, boolean append) {

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			int bytesToRead = newLength - oldLength;

			byte fileContentAsBytes[] = new byte[bytesToRead];
			bis.skip(oldLength);
			bis.read(fileContentAsBytes);
			String newLines = new String(fileContentAsBytes, "UTF-8");

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if (append) {
						textArea.appendText(newLines);
					} else {
						textArea.setText(newLines);
					}
				}
			});
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error " + e.getMessage(), e);
		}

	}

}
