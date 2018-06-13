package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

public class HighlightButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(HighlightButton.class.getName());

	public HighlightButton() {

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("HighlightButton -- Not implemented yet");
			}
		});

	}
}
