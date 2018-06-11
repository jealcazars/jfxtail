package com.jealcazars.jfxtail.control;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class MainMenu extends VBox {
	private static final Logger LOG = LoggerFactory.getLogger(MainMenu.class);

	public MainMenu() {
		FXMLViewLoader.load(this, "MainMenu.xml");
	}

	@FXML
	private void handleOpenAction(final ActionEvent event) {
		LOG.debug("handleOpenAction");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");

		File file = fileChooser.showOpenDialog(getScene().getWindow());

		if (file != null) {
			TabPane tabPane = (TabPane) getScene().lookup("#tabPane");
			TabFile tabFile = new TabFile(file, getScene());
			tabPane.getTabs().add(tabFile);
		}
	}

	@FXML
	private void handleHighlightsAction(final ActionEvent event) {
		LOG.debug("handleHighlightsAction");

	}
}
