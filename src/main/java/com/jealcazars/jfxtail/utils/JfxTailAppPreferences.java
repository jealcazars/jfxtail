package com.jealcazars.jfxtail.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import com.jealcazars.jfxtail.JfxTailApp;

public class JfxTailAppPreferences {

	private JfxTailAppPreferences() {

	}

	private static final Logger LOG = Logger.getLogger(JfxTailAppPreferences.class.getName());

	private static Preferences preferences = Preferences.userRoot().node(JfxTailApp.class.getName());

	public static final String LAST_KNOWN_FOLDER = "LAST_KNOWN_FOLDER";
	public static final String LAST_OPENED_FILES = "LAST_OPENED_FILES";

	public static final int LAST_OPENED_FILES_MAX_SIZE = 5;

	public static String getLastKnowFolder() {
		return preferences.get(LAST_KNOWN_FOLDER, null);
	}

	public static void setLastKnowFolder(String lastFolder) {
		preferences.put(LAST_KNOWN_FOLDER, lastFolder);
		LOG.fine("Saved " + LAST_KNOWN_FOLDER + " " + lastFolder);
	}

	public static List<String> getLastOpenedFiles() {
		return Arrays.asList(preferences.get(LAST_OPENED_FILES, "").split("#"));
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
}