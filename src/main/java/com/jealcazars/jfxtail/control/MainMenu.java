package com.jealcazars.jfxtail.control;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.menu.OpenMenuItem;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;
import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.VBox;

public class MainMenu extends VBox {
	private static final Logger LOG = Logger.getLogger(MainMenu.class.getName());

	@FXML
	Menu recentFiles;

	@FXML
	OpenMenuItem menuItemOpen;

	public MainMenu() {
		FXMLViewLoader.load(this, "MainMenu.xml");
		menuItemOpen.setParent(this);
		addClearButtonToRecentFiles();
		recentFiles.setOnShowing(event -> refreshRecentFiles());
	}

	private void refreshRecentFiles() {
		LOG.fine("Refreshing recent files");
		recentFiles.getItems().clear();

		List<String> recentFilesPath = JfxTailAppPreferences.getLastOpenFiles();

		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("recentFilesPath.size: " + recentFilesPath.size());
			LOG.fine("recentFilesPath: " + recentFilesPath);
		}

		for (Iterator<String> iterator = recentFilesPath.iterator(); iterator.hasNext();) {
			File file = new File(iterator.next());
			LOG.fine("Adding to recent files: " + file.getAbsolutePath());

			MenuItem menuItem = new MenuItem(file.getAbsolutePath());

			menuItem.setOnAction(event -> {
				LogFilesTabPane tabPane = LogFilesTabPane.getLogFilesTabPane(getScene());
				tabPane.addFile(file, false);
				refreshRecentFiles();
			});

			recentFiles.getItems().add(menuItem);
		}

		addClearButtonToRecentFiles();
	}

	private void addClearButtonToRecentFiles() {
		MenuItem clearRecentFiles = new MenuItem("Clear");

		clearRecentFiles.setOnAction(event -> {
			JfxTailAppPreferences.clearLastOpenFiles();
			refreshRecentFiles();
		});

		recentFiles.getItems().add(new SeparatorMenuItem());
		recentFiles.getItems().add(clearRecentFiles);
	}
}
