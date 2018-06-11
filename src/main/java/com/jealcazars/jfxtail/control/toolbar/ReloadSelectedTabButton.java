package com.jealcazars.jfxtail.control.toolbar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jealcazars.jfxtail.control.TabFile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

public class ReloadSelectedTabButton extends Button {
	private static final Logger LOG = LoggerFactory.getLogger(ReloadSelectedTabButton.class);

	public ReloadSelectedTabButton() {

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.debug("ReloadSelectedTabButton");
				TabPane tabPane = (TabPane) ((Node) event.getSource()).getScene().lookup("#tabPane");
				if (tabPane.getSelectionModel().getSelectedItem() != null) {
					((TabFile) tabPane.getSelectionModel().getSelectedItem()).reload();
				}
			}
		});

	}
}
