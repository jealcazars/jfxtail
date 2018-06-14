package com.jealcazars.jfxtail.control;

import java.io.File;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.scene.control.TabPane;

public class LogFilesTabPane extends TabPane {
	private static final Logger LOG = Logger.getLogger(LogFilesTabPane.class.getName());

	public void addFile(File file) {
		LOG.fine("Adding file to panel: " + file.getAbsolutePath());
		LogFileTab tabFile = new LogFileTab(file, getScene());
		getTabs().add(tabFile);

		JfxTailAppPreferences.setLastKnowFolder(file.getParentFile().getAbsolutePath());
		JfxTailAppPreferences.addToLastOpenedFiles(file.getAbsolutePath());
	}

}
