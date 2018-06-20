package com.jealcazars.jfxtail.control.menu;

import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.filter.FilterDialog;
import com.jealcazars.jfxtail.control.filter.text.TextFilter;
import com.jealcazars.jfxtail.control.filter.text.TextFilterPanel;
import com.jealcazars.jfxtail.control.filter.text.TextFilterProcessor;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.scene.control.MenuItem;

public class TextFilterMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(TextFilterMenuItem.class.getName());

	public TextFilterMenuItem() {
		setOnAction(event -> {
			LOG.fine("MenuHighlight");

			FilterDialog<TextFilter> dialog = new FilterDialog<>("Text Filters", new TextFilterPanel());

			Optional<LinkedList<TextFilter>> optional = dialog.showAndWait();
			optional.ifPresent(result -> {
				JfxTailAppPreferences.saveTextFilters(result);
				TextFilterProcessor.reloadFilters();
			});
		});
	}
}
