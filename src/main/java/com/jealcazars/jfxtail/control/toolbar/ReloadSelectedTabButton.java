package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;
import com.jealcazars.jfxtail.control.LogFilesTabPane;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class ReloadSelectedTabButton extends Button {
	private static final Logger LOG = Logger.getLogger(ReloadSelectedTabButton.class.getName());

	public ReloadSelectedTabButton() {

		setOnAction(event -> {
			LOG.fine("ReloadSelectedTabButton");
			LogFilesTabPane tabPane = LogFilesTabPane.getLogFilesTabPane(((Node) event.getSource()).getScene());

			if (tabPane.getSelectionModel().getSelectedItem() != null) {
				((LogFileTab) tabPane.getSelectionModel().getSelectedItem()).reload();
			}
		});
	}
}
