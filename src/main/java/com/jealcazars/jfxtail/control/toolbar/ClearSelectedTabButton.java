package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;
import com.jealcazars.jfxtail.control.LogFilesTabPane;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class ClearSelectedTabButton extends Button {
	private static final Logger LOG = Logger.getLogger(ClearSelectedTabButton.class.getName());

	public ClearSelectedTabButton() {

		setOnAction(event -> {
			LOG.fine("ClearSelectedTabButtonClick");
			LogFilesTabPane tabPane = LogFilesTabPane.getLogFilesTabPane(((Node) event.getSource()).getScene());
			if (tabPane.getSelectionModel().getSelectedItem() != null) {
				((LogFileTab) tabPane.getSelectionModel().getSelectedItem()).clear();
			}
		});
	}
}
