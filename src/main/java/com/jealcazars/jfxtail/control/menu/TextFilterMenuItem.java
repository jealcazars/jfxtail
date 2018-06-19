package com.jealcazars.jfxtail.control.menu;

import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.filter.FilterDialog;
import com.jealcazars.jfxtail.control.filter.text.TextFilter;
import com.jealcazars.jfxtail.control.filter.text.TextFilterPanel;
import com.jealcazars.jfxtail.control.filter.text.TextFilterProcessor;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class TextFilterMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(TextFilterMenuItem.class.getName());

	public TextFilterMenuItem() {
		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("MenuHighlight");

				FilterDialog<LinkedList<TextFilter>> dialog = new FilterDialog(new TextFilterPanel());

				Optional<LinkedList<TextFilter>> optional = dialog.showAndWait();
				optional.ifPresent(result -> {
					JfxTailAppPreferences.saveTextFilters(result);
					TextFilterProcessor.reloadFilters();
				});
			}
		});
	}

}
