package com.jealcazars.jfxtail.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import com.jealcazars.jfxtail.JfxTailApp;
import com.jealcazars.jfxtail.control.highlight.HighlightFilter;
import com.jealcazars.jfxtail.control.textfilter.TextFilter;

public class JfxTailAppPreferences {

	private JfxTailAppPreferences() {

	}

	private static final Logger LOG = Logger.getLogger(JfxTailAppPreferences.class.getName());

	private static Preferences preferences = Preferences.userRoot().node(JfxTailApp.class.getName());

	public static final String LAST_KNOWN_FOLDER = "LAST_KNOWN_FOLDER";
	public static final String LAST_OPENED_FILES = "LAST_OPENED_FILES";

	public static final int LAST_OPENED_FILES_MAX_SIZE = 5;

	private static final String HIGHLIGHT_FILTERS_NUM = "HIGHLIGHTFILTERS_NUM";
	private static final String HIGHLIGHT_FILTERS_PREFIX = "HIGHLIGHTFILTERS_";
	private static final String HIGHLIGHT_FILTERS_TOKEN_PREFIX = HIGHLIGHT_FILTERS_PREFIX + "TOKEN_";
	private static final String HIGHLIGHT_FILTERS_COLOR_PREFIX = HIGHLIGHT_FILTERS_PREFIX + "COLOR_";

	private static final String TEXT_FILTERS_NUM = "TEXTFILTERS_NUM";
	private static final String TEXT_FILTERS_PREFIX = "TEXTFILTERS_";
	private static final String TEXT_FILTERS_TOKEN_PREFIX = TEXT_FILTERS_PREFIX + "TOKEN_";

	public static String getLastKnowFolder() {
		return preferences.get(LAST_KNOWN_FOLDER, null);
	}

	public static void setLastKnowFolder(String lastFolder) {
		preferences.put(LAST_KNOWN_FOLDER, lastFolder);
		LOG.fine("Saved " + LAST_KNOWN_FOLDER + " " + lastFolder);
	}

	public static void clearLastOpenedFiles() {
		preferences.put(LAST_OPENED_FILES, "");
	}

	public static List<String> getLastOpenedFiles() {
		if (preferences.get(LAST_OPENED_FILES, "").equals("")) {
			return new ArrayList<>();
		} else {
			return Arrays.asList(preferences.get(LAST_OPENED_FILES, "").split("#"));
		}
	}

	public static void addToLastOpenedFiles(String file) {
		List<String> lastOpenedFiles = new ArrayList<>();
		lastOpenedFiles.addAll(getLastOpenedFiles());

		if (lastOpenedFiles.contains(file)) {
			lastOpenedFiles.remove(file);
		}

		lastOpenedFiles.add(0, file);

		if (lastOpenedFiles.size() > LAST_OPENED_FILES_MAX_SIZE) {
			lastOpenedFiles = lastOpenedFiles.subList(0, LAST_OPENED_FILES_MAX_SIZE);
		}

		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iterator = lastOpenedFiles.iterator(); iterator.hasNext();) {
			sb.append(iterator.next()).append("#");
		}

		preferences.put(LAST_OPENED_FILES, sb.toString());
		LOG.fine("Saved " + LAST_OPENED_FILES + " " + sb);
	}

	public static void saveHighlightFilters(LinkedList<HighlightFilter> highlightings) {
		preferences.putInt(HIGHLIGHT_FILTERS_NUM, highlightings.size());
		LOG.fine(HIGHLIGHT_FILTERS_NUM + ": " + highlightings.size());
		HighlightFilter highlighting = null;
		for (int i = 0; i < highlightings.size(); i++) {
			highlighting = highlightings.get(i);
			preferences.put(HIGHLIGHT_FILTERS_COLOR_PREFIX + i, highlighting.getColor());
			preferences.put(HIGHLIGHT_FILTERS_TOKEN_PREFIX + i, highlighting.getToken());
			LOG.fine(HIGHLIGHT_FILTERS_TOKEN_PREFIX + i + ": " + highlighting.getToken());
		}
	}

	public static LinkedList<HighlightFilter> loadHighlightFilters() {
		LinkedList<HighlightFilter> highlightings = new LinkedList<HighlightFilter>();
		int size = preferences.getInt(HIGHLIGHT_FILTERS_NUM, 0);
		if (size > 0) {
			String color;
			String token = null;
			for (int i = 0; i < size; i++) {
				color = preferences.get(HIGHLIGHT_FILTERS_COLOR_PREFIX + i, "Yellow");
				token = preferences.get(HIGHLIGHT_FILTERS_TOKEN_PREFIX + i, "");
				highlightings.add(new HighlightFilter(token, color));

			}
		}
		LOG.fine("HIGHLIGHTING: " + highlightings);

		return highlightings;
	}

	public static void saveTextFilters(LinkedList<TextFilter> textfilters) {
		preferences.putInt(TEXT_FILTERS_NUM, textfilters.size());
		LOG.fine(TEXT_FILTERS_NUM + ": " + textfilters.size());
		TextFilter textfilter = null;
		for (int i = 0; i < textfilters.size(); i++) {
			textfilter = textfilters.get(i);
			preferences.put(TEXT_FILTERS_TOKEN_PREFIX + i, textfilter.getToken());
			LOG.fine(TEXT_FILTERS_TOKEN_PREFIX + i + ": " + textfilter.getToken());
		}
	}

	public static LinkedList<TextFilter> loadTextFilters() {
		LinkedList<TextFilter> textfilters = new LinkedList<TextFilter>();
		int size = preferences.getInt(TEXT_FILTERS_NUM, 0);
		if (size > 0) {
			String token = null;
			for (int i = 0; i < size; i++) {
				token = preferences.get(TEXT_FILTERS_TOKEN_PREFIX + i, "");
				textfilters.add(new TextFilter(token));
			}
		}
		LOG.fine("Text_Filters: " + textfilters);

		return textfilters;
	}

}