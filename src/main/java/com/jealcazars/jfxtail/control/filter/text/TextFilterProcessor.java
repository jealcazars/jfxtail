package com.jealcazars.jfxtail.control.filter.text;

import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

public class TextFilterProcessor {
	private static final Logger LOG = Logger.getLogger(JfxTailAppPreferences.class.getName());

	private static Pattern pattern;

	public static void reloadFilters() {
		LinkedList<TextFilter> textFilters = JfxTailAppPreferences.loadTextFilters();

		StringBuilder patternSb = new StringBuilder();

		for (int i = 0; i < textFilters.size(); i++) {
			if (textFilters.get(i).isEnabled()) {
				if (patternSb.length() > 0) {
					patternSb.append("|");
				}
				patternSb.append("(?<filter").append(i).append(">").append(textFilters.get(i).getToken()).append(")");
			}
		}

		LOG.fine("TextFilterProcessor patternSb: " + patternSb);
		
		pattern = Pattern.compile(patternSb.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	}

	public static boolean lineMustBeAppended(String line) {
		if (pattern == null) {
			reloadFilters();
		}

		Matcher matcher = pattern.matcher(line);
		boolean matches = matcher.find();
		return matches;
	}
}
