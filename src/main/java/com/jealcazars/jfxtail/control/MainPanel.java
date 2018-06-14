package com.jealcazars.jfxtail.control;

import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainPanel extends VBox {

	@FXML
	public LogFilesTabPane logFilesTabPane;

	public MainPanel() {
		super();
		FXMLViewLoader.load(this, "MainPanel.xml");
		VBox.setVgrow(logFilesTabPane, Priority.ALWAYS);
	}

	@FXML
	private void handleExitAction(final ActionEvent event) {
		System.exit(0);
	}
}
