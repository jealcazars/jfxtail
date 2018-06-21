package com.jealcazars.jfxtail.control.toolbar;

import java.util.Iterator;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;
import com.jealcazars.jfxtail.control.LogFilesTabPane;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

public class ClearAllTabsButton extends Button {
	private static final Logger LOG = Logger.getLogger(ClearAllTabsButton.class.getName());

	public ClearAllTabsButton() {

		setOnAction(event -> {
			LOG.fine("ClearButtonClick");
			ObservableList<Tab> tabs = LogFilesTabPane.getLogFilesTabs(((Node) event.getSource()).getScene());
			for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
				LogFileTab tab = (LogFileTab) iterator.next();
				tab.clear();
			}
		});
	}
}
