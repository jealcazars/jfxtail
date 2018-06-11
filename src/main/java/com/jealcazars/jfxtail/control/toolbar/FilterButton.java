package com.jealcazars.jfxtail.control.toolbar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

public class FilterButton extends ToggleButton {
	private static final Logger LOG = LoggerFactory.getLogger(FilterButton.class);

	public FilterButton() {

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.debug("FilterButton");
			}
		});

	}
}
