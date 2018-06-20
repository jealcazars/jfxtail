package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import javafx.scene.control.ToggleButton;

public class SearchButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(SearchButton.class.getName());

	public SearchButton() {

		setOnAction(event -> {
			LOG.fine("SearchButton -- Not implemented yet");

		});

	}
}
