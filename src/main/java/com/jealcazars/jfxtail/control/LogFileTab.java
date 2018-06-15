package com.jealcazars.jfxtail.control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import com.jealcazars.jfxtail.control.highlight.HighlightFilter;
import com.jealcazars.jfxtail.file.FileListener;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

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
						LinkedList<HighlightFilter> highlightFilters = JfxTailAppPreferences.loadHighlightFilters();
						if (!highlightFilters.isEmpty()) {
							applyHighlighting(computeHighlighting(codeArea.getText(), highlightFilters));
						}
					} else if ("true".equals(System.getProperty("CleanHighlights"))) {
						cleanHighlighting(codeArea.getText());
					}
					
					codeArea.scrollBy(new Point2D(0, 10000));
				}
			});
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error " + e.getMessage(), e);
		}

	}

	private static StyleSpans<Collection<String>> computeHighlighting(String text,
			LinkedList<HighlightFilter> highlightFilters) {

		StringBuilder patternSb = new StringBuilder();

		for (int i = 0; i < highlightFilters.size(); i++) {
			if (patternSb.length() > 0) {
				patternSb.append("|");
			}
			patternSb.append("(?<filter").append(i).append(">").append(highlightFilters.get(i).getToken()).append(")");
		}

		LOG.fine("patternSb: " + patternSb);

		Pattern pattern = Pattern.compile(patternSb.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

		Matcher matcher = pattern.matcher(text);

		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {

			String styleClass = null;

			for (int i = 0; styleClass == null && i < highlightFilters.size(); i++) {
				styleClass = matcher.group("filter" + i) != null ? highlightFilters.get(i).getColor() : null;
			}

			assert styleClass != null;

			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}

		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);

		return spansBuilder.create();
	}

	private void cleanHighlighting(String text) {
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		spansBuilder.add(Collections.emptyList(), text.length() - 0);
		codeArea.setStyleSpans(0, spansBuilder.create());
	}

	private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
		codeArea.setStyleSpans(0, highlighting);
	}
}
