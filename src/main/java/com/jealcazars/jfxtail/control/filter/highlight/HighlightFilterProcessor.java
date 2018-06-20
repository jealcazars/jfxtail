package com.jealcazars.jfxtail.control.filter.highlight;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

public class HighlightFilterProcessor {
	private static final Logger LOG = Logger.getLogger(HighlightFilterProcessor.class.getName());

	private static Pattern pattern;
	private static List<HighlightFilter> highlightFilters;

	private static boolean activeHighlightFilters;

	private HighlightFilterProcessor() {
	}

	public static void reloadFilters() {
		highlightFilters = JfxTailAppPreferences.loadHighlightFilters();

		StringBuilder patternSb = new StringBuilder();
		activeHighlightFilters = false;

		for (int i = 0; i < highlightFilters.size(); i++) {
			if (highlightFilters.get(i).isEnabled()) {
				if (activeHighlightFilters) {
					patternSb.append("|");
				}
				patternSb.append("(?<filter").append(i).append(">").append(highlightFilters.get(i).getToken())
						.append(")");
				activeHighlightFilters = true;
			}
		}

		LOG.fine("HighlightFilterProcessor patternSb: " + patternSb);

		pattern = Pattern.compile(patternSb.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

	}

	public static void applyHighlighting(StyleClassedTextArea textarea) {
		if (pattern == null) {
			reloadFilters();
		}

		if (activeHighlightFilters) {
			applyHighlighting(textarea, computeHighlighting(textarea.getText()));
		} else {
			LOG.fine("Theres no highlight filters to apply");
		}
	}

	private static StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = pattern.matcher(text);

		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {

			String styleClass = null;

			for (int i = 0; styleClass == null && i < highlightFilters.size(); i++) {
				styleClass = highlightFilters.get(i).isEnabled() && matcher.group("filter" + i) != null
						? highlightFilters.get(i).getColor()
						: null;
			}

			assert styleClass != null;

			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}

		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);

		return spansBuilder.create();
	}

	public static void cleanHighlighting(StyleClassedTextArea textarea) {
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		spansBuilder.add(Collections.emptyList(), textarea.getText().length() - 0);
		textarea.setStyleSpans(0, spansBuilder.create());
	}

	private static void applyHighlighting(StyleClassedTextArea textarea, StyleSpans<Collection<String>> highlighting) {
		textarea.setStyleSpans(0, highlighting);
	}

}
