package com.jealcazars.jfxtail.control;

import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class MainPanel extends VBox {

	@FXML
	public TabPane tabPane;

	public MainPanel() {
		super();
		FXMLViewLoader.load(this, "MainPanel.xml");
	}

	@FXML
	private void handleExitAction(final ActionEvent event) {
		System.exit(0);
	}
}
