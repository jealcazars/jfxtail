package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

public class SearchButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(SearchButton.class.getName());

	public SearchButton() {

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("SearchButton -- Not implemented yet");
			}
		});

	}
}
