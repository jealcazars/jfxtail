package com.jealcazars.jfxtail.control;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;
import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class MainMenu extends VBox {
	private static final Logger LOG = Logger.getLogger(MainMenu.class.getName());

	@FXML
	Menu recentFiles;

	public MainMenu() {
		FXMLViewLoader.load(this, "MainMenu.xml");
		refreshRecentFiles();
	}

	@FXML
	private void handleOpenAction(final ActionEvent event) {
		LOG.fine("handleOpenAction");
		FileChooser fileChooser = new FileChooser();

		String lastKnownDirectory = JfxTailAppPreferences.getLastKnowFolder();

		if (lastKnownDirectory != null) {
			File folder = new File(lastKnownDirectory);
			if (folder.exists()) {
				fileChooser.setInitialDirectory(folder);
			}
		}

		fileChooser.setTitle("Open Resource File");

		File chosenFile = fileChooser.showOpenDialog(getScene().getWindow());

		if (chosenFile != null) {
			TabPaneFiles tabPane = (TabPaneFiles) getScene().lookup("#tabPane");
			tabPane.addFile(chosenFile);
			refreshRecentFiles();
		}
	}

	@FXML
	private void handleHighlightsAction(final ActionEvent event) {
		LOG.fine("handleHighlightsAction");
	}

	private void refreshRecentFiles() {
		recentFiles.getItems().clear();

		List<String> recentFilesPath = JfxTailAppPreferences.getLastOpenedFiles();
		for (Iterator<String> iterator = recentFilesPath.iterator(); iterator.hasNext();) {
			File file = new File(iterator.next());
			MenuItem menuItem = new MenuItem(file.getAbsolutePath());

			menuItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					TabPaneFiles tabPane = (TabPaneFiles) getScene().lookup("#tabPane");
					tabPane.addFile(file);
					refreshRecentFiles();
				}
			});

			recentFiles.getItems().add(menuItem);
		}
	}
}
