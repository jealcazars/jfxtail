package com.jealcazars.jfxtail.control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import com.jealcazars.jfxtail.control.highlight.HighlightFilterProcessor;
import com.jealcazars.jfxtail.file.FileListener;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;

public class LogFileTab extends Tab implements PropertyChangeListener {
	private static final Logger LOG = Logger.getLogger(LogFileTab.class.getName());

	File file;
	FileListener fileListener;
	private CodeArea codeArea = new CodeArea();

	private PropertyChangeSupport propertyChangeSupport;
	VirtualizedScrollPane<CodeArea> virtualizedScrollPane = new VirtualizedScrollPane<>(codeArea);

	public LogFileTab(File file, Scene scene) {
		super();
		propertyChangeSupport = new PropertyChangeSupport(this);

		this.file = file;

		this.setText(file.getName());

		setContent(virtualizedScrollPane);
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		fileListener = new FileListener(file);
		fileListener.addPropertyChangeListener(this);

		setOnClosed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				LOG.fine("Closing tab!");
				fileListener.stop();
			}
		});

		loadFile();
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void clear() {
		codeArea.clear();
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

	public void applyHighlightFilter() {
		HighlightFilterProcessor.applyHighlighting(codeArea);
	}

	public void cleanHighlightFilter() {
		HighlightFilterProcessor.cleanHighlighting(codeArea);
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
			String[] newLines = new String(fileContentAsBytes, "UTF-8").split("\n");

			// LOG.fine("Lines:" + LiveList.sizeOf(codeArea.getParagraphs()).getValue());

			Platform.runLater(new Runnable() {
				@Override
				public void run() {

					if (!append) {
						codeArea.clear();
					}

					for (int i = 0; i < newLines.length; i++) {
						codeArea.appendText(newLines[i]);
					}

					if ("false".equals(System.getProperty("CleanHighlights"))) {
						HighlightFilterProcessor.applyHighlighting(codeArea);
					} else if ("true".equals(System.getProperty("CleanHighlights"))) {
						HighlightFilterProcessor.cleanHighlighting(codeArea);
					}

					codeArea.scrollBy(new Point2D(0, 10000));
				}
			});
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error " + e.getMessage(), e);
		}
	}

}
