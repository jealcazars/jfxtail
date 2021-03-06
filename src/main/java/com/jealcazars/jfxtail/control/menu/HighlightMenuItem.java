package com.jealcazars.jfxtail.control.menu;

import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.filter.FilterDialog;
import com.jealcazars.jfxtail.control.filter.highlight.HighlightFilter;
import com.jealcazars.jfxtail.control.filter.highlight.HighlightFilterPanel;
import com.jealcazars.jfxtail.control.filter.highlight.HighlightFilterProcessor;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.scene.control.MenuItem;

public class HighlightMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(HighlightMenuItem.class.getName());

	public HighlightMenuItem() {
		setOnAction(event -> {
			LOG.fine("MenuHighlight");

			FilterDialog<HighlightFilter> dialog = new FilterDialog<>("Highlight filters", new HighlightFilterPanel());

			Optional<LinkedList<HighlightFilter>> optional = dialog.showAndWait();
			optional.ifPresent(result -> {
				JfxTailAppPreferences.saveHighlightFilters(result);
				HighlightFilterProcessor.reloadFilters();
			});
		});
	}

}
