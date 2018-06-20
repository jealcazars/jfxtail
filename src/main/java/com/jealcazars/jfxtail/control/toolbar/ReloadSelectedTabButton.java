package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

public class ReloadSelectedTabButton extends Button {
	private static final Logger LOG = Logger.getLogger(ReloadSelectedTabButton.class.getName());

	public ReloadSelectedTabButton() {

		setOnAction(event -> {
			LOG.fine("ReloadSelectedTabButton");
			TabPane tabPane = (TabPane) ((Node) event.getSource()).getScene().lookup("#logFilesTabPane");
			if (tabPane.getSelectionModel().getSelectedItem() != null) {
				((LogFileTab) tabPane.getSelectionModel().getSelectedItem()).reload();
			}
		});
	}
}
