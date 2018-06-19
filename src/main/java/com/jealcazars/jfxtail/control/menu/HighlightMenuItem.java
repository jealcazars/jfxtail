package com.jealcazars.jfxtail.control.menu;

import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.filter.FilterDialog;
import com.jealcazars.jfxtail.control.filter.highlight.HighlightFilter;
import com.jealcazars.jfxtail.control.filter.highlight.HighlightFilterPanel;
import com.jealcazars.jfxtail.control.filter.highlight.HighlightFilterProcessor;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class HighlightMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(HighlightMenuItem.class.getName());

	public HighlightMenuItem() {
		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("MenuHighlight");

				FilterDialog<HighlightFilter> dialog = new FilterDialog<>(new HighlightFilterPanel());

				Optional<LinkedList<HighlightFilter>> optional = dialog.showAndWait();
				optional.ifPresent(result -> {
					JfxTailAppPreferences.saveHighlightFilters(result);
					HighlightFilterProcessor.reloadFilters();
				});
			}
		});
	}

}
