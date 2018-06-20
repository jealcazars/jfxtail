package com.jealcazars.jfxtail.control.toolbar;

import java.util.Iterator;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class ReloadAllTabsButton extends Button {
	private static final Logger LOG = Logger.getLogger(ReloadAllTabsButton.class.getName());

	public ReloadAllTabsButton() {

		setOnAction(event -> {
			LOG.fine("ReloadAllTabsButton");
			TabPane tabPane = (TabPane) ((Node) event.getSource()).getScene().lookup("#logFilesTabPane");

			ObservableList<Tab> tabs = tabPane.getTabs();
			for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
				LogFileTab tab = (LogFileTab) iterator.next();
				tab.reload();
			}
		});
	}
}
