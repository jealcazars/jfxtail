package com.jealcazars.jfxtail.control;

import java.io.File;

import com.jealcazars.jfxtail.control.TabFile;
import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class MainMenu extends VBox {

	public MainMenu() {
		FXMLViewLoader.load(this, "MainMenu.xml");
	}

	@FXML
	private void handleOpenAction(final ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		// File file = fileChooser.showOpenDialog(primaryStage);
		File file = new File("D://prueba.log");

		TabPane tabPane = (TabPane) getScene().lookup("#tabPane");

		TabFile tabFile = new TabFile(file, getScene());
		tabPane.getTabs().add(tabFile);
	}
}
