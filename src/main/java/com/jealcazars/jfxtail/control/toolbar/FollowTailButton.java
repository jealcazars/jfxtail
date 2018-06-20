package com.jealcazars.jfxtail.control.toolbar;

import java.util.Iterator;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;

public class FollowTailButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(FollowTailButton.class.getName());
	boolean active = false;

	public FollowTailButton() {

		setOnAction(event -> {
			active = !active;

			LOG.fine("FollowTailButton active: " + active);

			TabPane tabPane = (TabPane) ((Node) event.getSource()).getScene().lookup("#logFilesTabPane");
			ObservableList<Tab> tabs = tabPane.getTabs();
			for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
				LogFileTab tab = (LogFileTab) iterator.next();

				tab.setFollowTailActive(active);
			}

		});
	}

	public boolean isActive() {
		return active;
	}
}
