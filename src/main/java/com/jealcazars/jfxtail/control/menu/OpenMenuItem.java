package com.jealcazars.jfxtail.control.menu;

import java.io.File;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.MainMenu;
import com.jealcazars.jfxtail.control.TabPaneFiles;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

public class OpenMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(OpenMenuItem.class.getName());

	MainMenu parent;

	public OpenMenuItem() {

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("MenuItemOpen");
				FileChooser fileChooser = new FileChooser();

				String lastKnownDirectory = JfxTailAppPreferences.getLastKnowFolder();

				if (lastKnownDirectory != null) {
					File folder = new File(lastKnownDirectory);
					if (folder.exists()) {
						fileChooser.setInitialDirectory(folder);
					}
				}

				fileChooser.setTitle("Open Resource File");

				File chosenFile = fileChooser.showOpenDialog(parent.getScene().getWindow());

				if (chosenFile != null) {
					TabPaneFiles tabPane = (TabPaneFiles) parent.getScene().lookup("#tabPane");
					tabPane.addFile(chosenFile);
					parent.refreshRecentFiles();
				}
			}
		});
	}

	public void setParent(MainMenu parent) {
		this.parent = parent;
	}

}
