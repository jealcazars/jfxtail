package com.jealcazars.jfxtail.control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import com.jealcazars.jfxtail.file.FileListener;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import test.JavaKeywordsAsyncDemo;

public class LogFileTab extends Tab implements PropertyChangeListener {
	private static final Logger LOG = Logger.getLogger(LogFileTab.class.getName());

	File file;
	FileListener fileListener;
	private CodeArea codeArea = new CodeArea();

	private PropertyChangeSupport propertyChangeSupport;

	public LogFileTab(File file, Scene scene) {
		super();
		propertyChangeSupport = new PropertyChangeSupport(this);

		this.file = file;

		this.setText(file.getName());
		VirtualizedScrollPane<CodeArea> virtualizedScrollPane = new VirtualizedScrollPane<>(codeArea);
		setContent(virtualizedScrollPane);
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

		scene.getStylesheets().add(JavaKeywordsAsyncDemo.class.getResource("java-keywords.css").toExternalForm());

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

					if ("true".equals(System.getProperty("HighlightButton")) && System.getProperty("highlight") != null
							&& System.getProperty("highlight").trim().length() > 0) {
						applyHighlighting(computeHighlighting(codeArea.getText()));
					}

					if ("true".equals(System.getProperty("CleanHighlights"))) {
						cleanHighlighting(codeArea.getText());
					}
				}
			});
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error " + e.getMessage(), e);
		}

	}

	private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

	private static StyleSpans<Collection<String>> computeHighlighting(String text) {
		String highlight = System.getProperty("highlight");

		StringBuilder patternSb = new StringBuilder(
				"(?<KEYWORD>" + "\\b" + highlight + "\\b" + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")");

		// Pattern PATTERN = Pattern.compile("(?<KEYWORD>" + "\\b(" + String.join("|",
		// KEYWORDS) + ")\\b" + ")"
		Pattern PATTERN = Pattern.compile(patternSb.toString(), Pattern.CASE_INSENSITIVE);

		Matcher matcher = PATTERN.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			String styleClass = matcher.group("KEYWORD") != null ? "keyword"
					: matcher.group("COMMENT") != null ? "comment" : null;

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
