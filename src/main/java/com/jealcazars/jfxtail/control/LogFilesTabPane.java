package com.jealcazars.jfxtail.control;

import java.io.File;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.toolbar.FilterButton;
import com.jealcazars.jfxtail.control.toolbar.HighlightButton;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.scene.control.TabPane;

public class LogFilesTabPane extends TabPane {
	private static final Logger LOG = Logger.getLogger(LogFilesTabPane.class.getName());

	public void addFile(File file, boolean setLastKnowFolder) {
		LOG.fine("Adding file to panel: " + file.getAbsolutePath());
		LogFileTab tabFile = new LogFileTab(file, getScene());

		FilterButton filterButton = (FilterButton) getScene().lookup("#filterButton");

		if (filterButton != null && filterButton.isActive()) {
			LOG.fine("Filter button is active, set tab filter active");
			tabFile.setFilterActive(true);
		}

		HighlightButton highlightButton = (HighlightButton) getScene().lookup("#highlightButton");

		if (highlightButton != null && highlightButton.isActive()) {
			LOG.fine("highlightButton is active, set tab filter active");
			tabFile.setHighlightActive(true);
		}

		getTabs().add(tabFile);

		if (setLastKnowFolder) {
			JfxTailAppPreferences.setLastKnowFolder(file.getParentFile().getAbsolutePath());
		}

		JfxTailAppPreferences.addToLastOpenedFiles(file.getAbsolutePath());
	}

}
