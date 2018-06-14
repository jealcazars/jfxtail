package com.jealcazars.jfxtail.control.menu;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

public class HighlightMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(HighlightMenuItem.class.getName());

	public HighlightMenuItem() {
		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("MenuHighlight -- Not implemented yet");

			}
		});
	}

}
