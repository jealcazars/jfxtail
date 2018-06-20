package com.jealcazars.jfxtail.control;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

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
	}

	public void addFile(File file, boolean setLastKnowFolder) {
		LOG.fine("Adding file to panel: " + file.getAbsolutePath());
		LogFileTab tabFile = new LogFileTab(file);

		getTabs().add(tabFile);

		if (setLastKnowFolder) {
			JfxTailAppPreferences.setLastKnowFolder(file.getParentFile().getAbsolutePath());
		}

		JfxTailAppPreferences.addToLastOpenFiles(file.getAbsolutePath());
	}

}
