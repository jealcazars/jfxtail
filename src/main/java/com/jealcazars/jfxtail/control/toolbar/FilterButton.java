package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.scene.control.ToggleButton;

public class FilterButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(FilterButton.class.getName());

	public FilterButton() {
		setOnAction(event -> {
			LOG.fine("Text Filter active: " + isSelected());
			JfxTailAppPreferences.setFilterActive(isSelected());
		});
	}

}
