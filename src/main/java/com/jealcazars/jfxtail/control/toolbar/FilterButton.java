package com.jealcazars.jfxtail.control.toolbar;

import java.util.Iterator;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;

public class FilterButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(FilterButton.class.getName());
	boolean active = false;

	public FilterButton() {

		setOnAction(event -> {
			active = !active;

			LOG.fine("Text Filter active: " + active);

			TabPane tabPane = (TabPane) ((Node) event.getSource()).getScene().lookup("#logFilesTabPane");
			ObservableList<Tab> tabs = tabPane.getTabs();
			for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
				((LogFileTab) iterator.next()).setFilterActive(active);
			}
		});
	}

	public boolean isActive() {
		return active;
	}
}
