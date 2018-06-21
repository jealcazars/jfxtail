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
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class LogFileTab extends Tab implements PropertyChangeListener {
	private static final Logger LOG = Logger.getLogger(LogFileTab.class.getName());

	File file;
	FileListener fileListener;
	CodeArea codeArea = new CodeArea();
	int linesAlreadyAdded = 0;
	boolean isSelected;

	private PropertyChangeSupport propertyChangeSupport;
	VirtualizedScrollPane<CodeArea> virtualizedScrollPane = new VirtualizedScrollPane<>(codeArea);

	public LogFileTab(File file) {
		super();
		propertyChangeSupport = new PropertyChangeSupport(this);

		this.file = file;
		this.setText(file.getName());
		setContent(virtualizedScrollPane);
		setTooltip(new Tooltip(file.getAbsolutePath()));

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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (FileListener.FILE_WAS_MODIFIED.equals(evt.getPropertyName())) {
			long lastLength = (Long) evt.getOldValue();
			long newLength = (Long) evt.getNewValue();
			loadFilesChanges((int) lastLength, (int) newLength, true);
		}
	}

	private synchronized void loadFile() {
		loadFilesChanges(0, (int) file.length(), false);
	}

	private synchronized void loadFilesChanges(int oldLength, int newLength, boolean append) {
		String[] newLines = reafFile(oldLength, newLength);

		Platform.runLater(() -> {

			if (!append) {
				clear();
			}

			boolean added = false;

			for (int i = 0; newLines != null && i < newLines.length; i++) {
				if (lineMustBeAdded(newLines[i])) {
					codeArea.appendText(newLines[i]);
					linesAlreadyAdded++;

					if (linesAlreadyAdded > JfxTailAppPreferences.getMaxLines()) {
						codeArea.replaceText(0, codeArea.getParagraph(0).length() + 1, "");
					}

					added = true;
				}
			}

			if (added) {
				postAddedActions();
			}
		});
	}

	private String[] reafFile(int oldLength, int newLength) {
		String[] newLines = null;
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			int bytesToRead = newLength - oldLength;

			if (bytesToRead > JfxTailAppPreferences.getBufferSize()) {
				LOG.fine("bytesToRead bigger than buffer size, some bytes will be ignored");
				bytesToRead = JfxTailAppPreferences.getBufferSize();
			}

			byte[] fileContentAsBytes = new byte[bytesToRead];
			bis.skip((long) newLength - (long) bytesToRead);
			if (bis.read(fileContentAsBytes) > 0) {
				newLines = new String(fileContentAsBytes, "UTF-8").split("\n");
			}
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error " + e.getMessage(), e);
		}
		return newLines;
	}

	private boolean lineMustBeAdded(String line) {
		return !JfxTailAppPreferences.isFilterActive()
				|| (JfxTailAppPreferences.isFilterActive() && TextFilterProcessor.lineMustBeAppended(line));
	}

	private void postAddedActions() {
		if (JfxTailAppPreferences.isHighlightActive()) {
			HighlightFilterProcessor.applyHighlighting(codeArea);
		}

		if (JfxTailAppPreferences.isFollowTailActive()) {
			codeArea.scrollBy(new Point2D(0, 10000));
		}

		if (!isSelected()) {
			setGraphic(new Rectangle(8, 8, Paint.valueOf("black")));
		}
	}

	public void clear() {
		codeArea.clear();
		linesAlreadyAdded = 0;
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

	public String getFilePath() {
		return file.getAbsolutePath();
	}

	public void applyHighlightFilter() {
		HighlightFilterProcessor.applyHighlighting(codeArea);
	}

	public void cleanHighlightFilter() {
		HighlightFilterProcessor.cleanHighlighting(codeArea);
	}
}
