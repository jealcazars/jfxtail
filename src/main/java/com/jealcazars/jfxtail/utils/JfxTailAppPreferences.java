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

public class JfxTailAppPreferences {

	private JfxTailAppPreferences() {

	}

	private static final Logger LOG = Logger.getLogger(JfxTailAppPreferences.class.getName());

	private static Preferences preferences = Preferences.userRoot().node(JfxTailApp.class.getName());

	public static final String LAST_KNOWN_FOLDER = "LAST_KNOWN_FOLDER";
	public static final String LAST_OPENED_FILES = "LAST_OPENED_FILES";

	public static final int LAST_OPENED_FILES_MAX_SIZE = 5;

	private static final String HIGHLIGHTFILTERS_NUM = "HIGHLIGHTFILTERS_NUM";
	private static final String HIGHLIGHTFILTERS_PREFIX = "HIGHLIGHTFILTERS_";
	private static final String HIGHLIGHTFILTERS_TOKEN_PREFIX = HIGHLIGHTFILTERS_PREFIX + "TOKEN_";
	private static final String HIGHLIGHTFILTERS_COLOR_PREFIX = HIGHLIGHTFILTERS_PREFIX + "COLOR_";

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
		preferences.putInt(HIGHLIGHTFILTERS_NUM, highlightings.size());
		LOG.fine(HIGHLIGHTFILTERS_NUM + ": " + highlightings.size());
		HighlightFilter highlighting = null;
		for (int i = 0; i < highlightings.size(); i++) {
			highlighting = highlightings.get(i);
			preferences.put(HIGHLIGHTFILTERS_COLOR_PREFIX + i, highlighting.getColor());
			preferences.put(HIGHLIGHTFILTERS_TOKEN_PREFIX + i, highlighting.getToken());
			LOG.fine(HIGHLIGHTFILTERS_TOKEN_PREFIX + i + ": " + highlighting.getToken());
		}
	}

	public static LinkedList<HighlightFilter> loadHighlightFilters() {
		LinkedList<HighlightFilter> highlightings = new LinkedList<HighlightFilter>();
		int size = preferences.getInt(HIGHLIGHTFILTERS_NUM, 0);
		if (size > 0) {
			String color;
			String token = null;
			for (int i = 0; i < size; i++) {
				color = preferences.get(HIGHLIGHTFILTERS_COLOR_PREFIX + i, "Yellow");
				token = preferences.get(HIGHLIGHTFILTERS_TOKEN_PREFIX + i, "");
				highlightings.add(new HighlightFilter(token, color));

			}
		}
		LOG.fine("HIGHLIGHTING: " + highlightings);

		return highlightings;
	}
}