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

import com.jealcazars.jfxtail.control.filter.highlight.HighlightFilterProcessor;
import com.jealcazars.jfxtail.control.filter.text.TextFilterProcessor;
import com.jealcazars.jfxtail.file.FileListener;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;

public class LogFileTab extends Tab implements PropertyChangeListener {
	private static final Logger LOG = Logger.getLogger(LogFileTab.class.getName());

	File file;
	FileListener fileListener;
	private CodeArea codeArea = new CodeArea();
	boolean filterActive = false;
	boolean highlightActive = false;
	boolean followTailActive = false;
	int linesAlreadyAdded = 0;

	private PropertyChangeSupport propertyChangeSupport;
	VirtualizedScrollPane<CodeArea> virtualizedScrollPane = new VirtualizedScrollPane<>(codeArea);

	public LogFileTab(File file, Scene scene) {
		super();
		propertyChangeSupport = new PropertyChangeSupport(this);

		this.file = file;

		this.setText(file.getName());
		setContent(virtualizedScrollPane);

		// TODO review if needed
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

		fileListener = new FileListener(file);
		fileListener.addPropertyChangeListener(this);

		setOnClosed(event -> {
			LOG.fine("Closing tab!");
			fileListener.stop();
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

	public boolean isHighlightActive() {
		return highlightActive;
	}

	public void setHighlightActive(boolean highlightActive) {
		this.highlightActive = highlightActive;
	}

	public boolean isFilterActive() {
		return filterActive;
	}

	public void setFilterActive(boolean filterActive) {
		this.filterActive = filterActive;
	}

	public int getLinesAlreadyAdded() {
		return linesAlreadyAdded;
	}

	public void setFollowTailActive(boolean followTailActive) {
		this.followTailActive = followTailActive;
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

			if (bytesToRead > JfxTailAppPreferences.getBufferSize()) {
				LOG.fine("bytesToRead bigger than buffer size, some bytes will be ignored");
				bytesToRead = JfxTailAppPreferences.getBufferSize();
			}

			byte fileContentAsBytes[] = new byte[bytesToRead];
			bis.skip(newLength - bytesToRead);

			bis.read(fileContentAsBytes);
			String[] newLines = new String(fileContentAsBytes, "UTF-8").split("\n");

			Platform.runLater(() -> {

				if (!append) {
					codeArea.clear();
					linesAlreadyAdded = 0;
				}

				for (int i = 0; i < newLines.length; i++) {
					if (!isFilterActive()
							|| (isFilterActive() && TextFilterProcessor.lineMustBeAppended(newLines[i]))) {
						codeArea.appendText(newLines[i]);
						linesAlreadyAdded++;

						if (linesAlreadyAdded > JfxTailAppPreferences.getMaxLines()) {
							codeArea.replaceText(0, codeArea.getParagraph(0).length() + 1, "");
						}
					}
				}

				if (isHighlightActive()) {
					HighlightFilterProcessor.applyHighlighting(codeArea);
				}

				if (followTailActive) {
					codeArea.scrollBy(new Point2D(0, 10000));
				}

			});
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error " + e.getMessage(), e);
		}
	}
}
