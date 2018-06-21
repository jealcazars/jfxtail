package com.jealcazars.jfxtail.control;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class LogFilesTabPane extends TabPane {
	private static final Logger LOG = Logger.getLogger(LogFilesTabPane.class.getName());

	public LogFilesTabPane() {
		List<String> files = JfxTailAppPreferences.getOpenFiles();

		for (Iterator<String> iterator = files.iterator(); iterator.hasNext();) {
			File file = new File(iterator.next());
			if (file.exists()) {
				addFile(file, false);
			} else {
				LOG.fine("File doesnt exists" + file.getAbsolutePath());
			}
		}

		getSelectionModel().selectedItemProperty().addListener((event, oldValue, newValue) -> {
			if (newValue != null) {
				newValue.setGraphic(null);
			}
		});
	}

	public void addFile(File file, boolean setLastKnowFolder) {
		if (!isAlreadyOpen(file.getAbsolutePath())) {
			LOG.fine("Adding file to panel: " + file.getAbsolutePath());
			LogFileTab tabFile = new LogFileTab(file);

			getTabs().add(tabFile);

			if (setLastKnowFolder) {
				JfxTailAppPreferences.setLastKnowFolder(file.getParentFile().getAbsolutePath());
			}

			JfxTailAppPreferences.addToLastOpenFiles(file.getAbsolutePath());
		} else {
			LOG.fine("File already open");
			Alert alert = new Alert(AlertType.NONE, "File is already open ", ButtonType.OK);
			alert.setTitle("Warning");
			alert.showAndWait();
		}
	}

	private boolean isAlreadyOpen(String absolutePath) {
		Iterator<Tab> tabIt = getTabs().iterator();
		while (tabIt.hasNext()) {
			LogFileTab tab = (LogFileTab) tabIt.next();
			if (tab.getFilePath().equals(absolutePath)) {
				return true;
			}
		}
		return false;
	}

}
