package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

public class FilterButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(FilterButton.class.getName());

	public FilterButton() {

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("FilterButton -- Not implemented yet");
			}
		});

	}
}
