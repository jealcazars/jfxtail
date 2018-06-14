package com.jealcazars.jfxtail.control;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.menu.OpenMenuItem;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;
import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class MainMenu extends VBox {
	private static final Logger LOG = Logger.getLogger(MainMenu.class.getName());

	@FXML
	Menu recentFiles;

	@FXML
	OpenMenuItem menuItemOpen;

	public MainMenu() {
		FXMLViewLoader.load(this, "MainMenu.xml");
		refreshRecentFiles();
		menuItemOpen.setParent(this);
	}

	public void refreshRecentFiles() {
		LOG.fine("Refreshing recent files");
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
