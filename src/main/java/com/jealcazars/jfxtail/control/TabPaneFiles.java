package com.jealcazars.jfxtail.control;

import java.io.File;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.scene.control.TabPane;

public class TabPaneFiles extends TabPane {

	public void addFile(File file) {
		TabFile tabFile = new TabFile(file, getScene());
		getTabs().add(tabFile);

		JfxTailAppPreferences.setLastKnowFolder(file.getParentFile().getAbsolutePath());
		JfxTailAppPreferences.addToLastOpenedFiles(file.getAbsolutePath());
	}

}
