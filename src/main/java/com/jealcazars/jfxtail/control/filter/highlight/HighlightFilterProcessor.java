package com.jealcazars.jfxtail.control.filter.highlight;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.StyleClassedTextArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

public class HighlightFilterProcessor {
	private static final Logger LOG = Logger.getLogger(HighlightFilterProcessor.class.getName());

	public static void applyHighlighting(StyleClassedTextArea textarea) {

		LinkedList<HighlightFilter> highlightFilters = JfxTailAppPreferences.loadHighlightFilters();
		if (!highlightFilters.isEmpty()) {
			applyHighlighting(textarea, computeHighlighting(textarea.getText(), highlightFilters));
		}
	}

	private static StyleSpans<Collection<String>> computeHighlighting(String text,
			LinkedList<HighlightFilter> highlightFilters) {

		// TODO load statically
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

	public static void cleanHighlighting(StyleClassedTextArea textarea) {
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		spansBuilder.add(Collections.emptyList(), textarea.getText().length() - 0);
		textarea.setStyleSpans(0, spansBuilder.create());
	}

	private static void applyHighlighting(StyleClassedTextArea textarea, StyleSpans<Collection<String>> highlighting) {
		textarea.setStyleSpans(0, highlighting);
	}

}
