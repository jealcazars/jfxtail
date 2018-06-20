package com.jealcazars.jfxtail.control.filter.text;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

public class TextFilterProcessor {
	private static final Logger LOG = Logger.getLogger(JfxTailAppPreferences.class.getName());

	private static List<TextFilter> textFilters;

	private TextFilterProcessor() {
	}

	public static void reloadFilters() {
		textFilters = JfxTailAppPreferences.loadTextFilters();
	}

	public static boolean lineMustBeAppended(String line) {
		if (textFilters == null) {
			reloadFilters();
		}

		for (int i = 0; i < textFilters.size(); i++) {
			if (textFilters.get(i).isEnabled()) {
				Matcher matcher = Pattern
						.compile(textFilters.get(i).getToken(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
						.matcher(line);

				if (matcher.find()) {
					LOG.fine("filterMatched: " + textFilters.get(i));
					return textFilters.get(i).isIncludeType();
				}
			}
		}

		return false;
	}
}
