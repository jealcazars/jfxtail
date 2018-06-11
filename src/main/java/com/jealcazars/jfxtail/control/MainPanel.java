package com.jealcazars.jfxtail.control;

import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPanel extends VBox {

	Stage stage;

	@FXML
	public TabPane tabPane;

	public MainPanel(Stage stage) {
		super();
		this.stage = stage;
		FXMLViewLoader.load(this, "MainPanel.xml");
	}


	@FXML
	private void handleExitAction(final ActionEvent event) {
		System.exit(0);
	}

	public Stage getStage() {
		return stage;
	}

}
