package com.jealcazars.jfxtail.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import com.jealcazars.jfxtail.JfxTailApp;
import com.jealcazars.jfxtail.control.filter.highlight.HighlightFilter;
import com.jealcazars.jfxtail.control.filter.text.TextFilter;

public class JfxTailAppPreferences {

	private JfxTailAppPreferences() {

	}

	private static final Logger LOG = Logger.getLogger(JfxTailAppPreferences.class.getName());

	private static Preferences preferences = Preferences.userRoot().node(JfxTailApp.class.getName());

	public static final String LAST_KNOWN_FOLDER = "LAST_KNOWN_FOLDER";
	public static final String LAST_OPEN_FILES = "LAST_OPEN_FILES";
	public static final String OPEN_FILES = "OPEN_FILES";

	public static final int LAST_OPEN_FILES_MAX_SIZE = 5;

	private static final String HIGHLIGHT_FILTERS_NUM = "HIGHLIGHTFILTERS_NUM";
	private static final String HIGHLIGHT_FILTERS_PREFIX = "HIGHLIGHTFILTERS_";
	private static final String HIGHLIGHT_FILTERS_ENABLED_PREFIX = HIGHLIGHT_FILTERS_PREFIX + "ENABLED_";
	private static final String HIGHLIGHT_FILTERS_TOKEN_PREFIX = HIGHLIGHT_FILTERS_PREFIX + "TOKEN_";
	private static final String HIGHLIGHT_FILTERS_COLOR_PREFIX = HIGHLIGHT_FILTERS_PREFIX + "COLOR_";

	private static final String TEXT_FILTERS_NUM = "TEXTFILTERS_NUM";
	private static final String TEXT_FILTERS_PREFIX = "TEXTFILTERS_";
	private static final String TEXT_FILTERS_ENABLED_PREFIX = TEXT_FILTERS_PREFIX + "ENABLED_";
	private static final String TEXT_FILTERS_TOKEN_PREFIX = TEXT_FILTERS_PREFIX + "TOKEN_";
	private static final String TEXT_FILTERS_TYPE_PREFIX = TEXT_FILTERS_PREFIX + "TYPE_";

	private static int maxLines;
	private static final String MAX_LINES_KEY = "MAX_LINES";

	private static int refreshRate;
	private static final String REFRESH_RATE_KEY = "REFRESH_RATE";

	private static int bufferSize;
	private static final String BUFFER_SIZE_KEY = "BUFFER_SIZE";

	private static boolean followTail;
	private static final String FOLLOW_TAIL_KEY = "FOLLOW_TAIL";

	private static boolean filterActive;
	private static final String FILTER_ACTIVE_KEY = "FILTER_ACTIVE";

	private static boolean highlightActive;
	private static final String HIGHLIGHT_ACTIVE_KEY = "HIGHLIGHT_ACTIVE";

	static {
		maxLines = preferences.getInt(MAX_LINES_KEY, 2000);
		refreshRate = preferences.getInt(REFRESH_RATE_KEY, 200);
		bufferSize = preferences.getInt(BUFFER_SIZE_KEY, 10000000);
		followTail = preferences.getBoolean(FOLLOW_TAIL_KEY, false);
		filterActive = preferences.getBoolean(FILTER_ACTIVE_KEY, false);
		highlightActive = preferences.getBoolean(HIGHLIGHT_ACTIVE_KEY, false);
	}

	public static boolean isHighlightActive() {
		return highlightActive;
	}

	public static void setHighlightActive(boolean value) {
		highlightActive = value;
		preferences.putBoolean(HIGHLIGHT_ACTIVE_KEY, value);
	}

	public static boolean isFilterActive() {
		return filterActive;
	}

	public static void setFilterActive(boolean value) {
		filterActive = value;
		preferences.putBoolean(FILTER_ACTIVE_KEY, value);
	}

	public static boolean isFollowTailActive() {
		return followTail;
	}

	public static void setFollowTail(boolean value) {
		followTail = value;
		preferences.putBoolean(FOLLOW_TAIL_KEY, value);
	}

	public static int getMaxLines() {
		return maxLines;
	}

	public static void setMaxLines(int value) {
		maxLines = value;
		preferences.putInt(MAX_LINES_KEY, value);
	}

	public static int getRefreshRate() {
		return refreshRate;
	}

	public static void setRefreshRate(int value) {
		refreshRate = value;
		preferences.putInt(REFRESH_RATE_KEY, value);
	}

	public static int getBufferSize() {
		return bufferSize;
	}

	public static void setBufferSize(int value) {
		bufferSize = value;
		preferences.putInt(BUFFER_SIZE_KEY, value);
	}

	public static String getLastKnowFolder() {
		return preferences.get(LAST_KNOWN_FOLDER, null);
	}

	public static void setLastKnowFolder(String lastFolder) {
		preferences.put(LAST_KNOWN_FOLDER, lastFolder);
		LOG.fine("Saved " + LAST_KNOWN_FOLDER + " " + lastFolder);
	}

	public static void clearLastOpenFiles() {
		preferences.put(LAST_OPEN_FILES, "");
	}

	public static List<String> getLastOpenFiles() {
		if (preferences.get(LAST_OPEN_FILES, "").equals("")) {
			return new ArrayList<>();
		} else {
			return Arrays.asList(preferences.get(LAST_OPEN_FILES, "").split("#"));
		}
	}

	public static void addToLastOpenFiles(String file) {
		List<String> lastOpenFiles = new ArrayList<>();
		lastOpenFiles.addAll(getLastOpenFiles());

		if (lastOpenFiles.contains(file)) {
			lastOpenFiles.remove(file);
		}

		lastOpenFiles.add(0, file);

		if (lastOpenFiles.size() > LAST_OPEN_FILES_MAX_SIZE) {
			lastOpenFiles = lastOpenFiles.subList(0, LAST_OPEN_FILES_MAX_SIZE);
		}

		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iterator = lastOpenFiles.iterator(); iterator.hasNext();) {
			sb.append(iterator.next()).append("#");
		}

		preferences.put(LAST_OPEN_FILES, sb.toString());
		LOG.fine("Saved " + LAST_OPEN_FILES + " " + sb);
	}

	public static List<String> getOpenFiles() {
		if (preferences.get(OPEN_FILES, "").equals("")) {
			return new ArrayList<>();
		} else {
			return Arrays.asList(preferences.get(OPEN_FILES, "").split("#"));
		}
	}

	public static void saveOpenFiles(List<String> files) {
		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iterator = files.iterator(); iterator.hasNext();) {
			sb.append(iterator.next()).append("#");
		}

		preferences.put(OPEN_FILES, sb.toString());
		LOG.fine("Saved " + OPEN_FILES + " " + sb);
	}

	public static void saveHighlightFilters(List<HighlightFilter> highlightings) {
		preferences.putInt(HIGHLIGHT_FILTERS_NUM, highlightings.size());
		LOG.fine(HIGHLIGHT_FILTERS_NUM + ": " + highlightings.size());
		HighlightFilter highlighting = null;
		for (int i = 0; i < highlightings.size(); i++) {
			highlighting = highlightings.get(i);
			preferences.put(HIGHLIGHT_FILTERS_ENABLED_PREFIX + i, String.valueOf(highlighting.isEnabled()));
			preferences.put(HIGHLIGHT_FILTERS_COLOR_PREFIX + i, highlighting.getColor());
			preferences.put(HIGHLIGHT_FILTERS_TOKEN_PREFIX + i, highlighting.getToken());
			LOG.fine(HIGHLIGHT_FILTERS_TOKEN_PREFIX + i + ": " + highlighting.getToken());
		}
	}

	public static List<HighlightFilter> loadHighlightFilters() {
		List<HighlightFilter> highlightings = new LinkedList<>();
		int size = preferences.getInt(HIGHLIGHT_FILTERS_NUM, 0);
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				highlightings.add(
						new HighlightFilter("true".equals(preferences.get(HIGHLIGHT_FILTERS_ENABLED_PREFIX + i, "")),
								preferences.get(HIGHLIGHT_FILTERS_TOKEN_PREFIX + i, ""),
								preferences.get(HIGHLIGHT_FILTERS_COLOR_PREFIX + i, "Yellow")));

			}
		}
		LOG.fine("HIGHLIGHTING: " + highlightings);

		return highlightings;
	}

	public static void saveTextFilters(List<TextFilter> textfilters) {
		preferences.putInt(TEXT_FILTERS_NUM, textfilters.size());
		LOG.fine(TEXT_FILTERS_NUM + ": " + textfilters.size());
		TextFilter textfilter = null;
		for (int i = 0; i < textfilters.size(); i++) {
			textfilter = textfilters.get(i);
			preferences.put(TEXT_FILTERS_ENABLED_PREFIX + i, String.valueOf(textfilter.isEnabled()));
			preferences.put(TEXT_FILTERS_TOKEN_PREFIX + i, textfilter.getToken());
			preferences.put(TEXT_FILTERS_TYPE_PREFIX + i, textfilter.getType());
			LOG.fine(TEXT_FILTERS_TOKEN_PREFIX + i + ": " + textfilter.getToken());
			LOG.fine(TEXT_FILTERS_TYPE_PREFIX + i + ": " + textfilter.getType());

		}
	}

	public static List<TextFilter> loadTextFilters() {
		List<TextFilter> textfilters = new LinkedList<>();
		int size = preferences.getInt(TEXT_FILTERS_NUM, 0);
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				textfilters.add(new TextFilter("true".equals(preferences.get(TEXT_FILTERS_ENABLED_PREFIX + i, "")),
						preferences.get(TEXT_FILTERS_TOKEN_PREFIX + i, ""),
						preferences.get(TEXT_FILTERS_TYPE_PREFIX + i, "")));
			}
		}
		LOG.fine("Text_Filters: " + textfilters);

		return textfilters;
	}

}