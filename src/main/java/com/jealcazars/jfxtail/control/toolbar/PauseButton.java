package com.jealcazars.jfxtail.control.toolbar;

import java.util.Iterator;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;
import com.jealcazars.jfxtail.control.LogFilesTabPane;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;

public class PauseButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(PauseButton.class.getName());

	boolean active = false;

	public PauseButton() {

		setOnAction(event -> {
			active = !active;
			LOG.fine("PauseButtonClick active " + active);

			if (active) {
				ObservableList<Tab> tabs = LogFilesTabPane.getLogFilesTabs(((Node) event.getSource()).getScene());
				for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
					LogFileTab tab = (LogFileTab) iterator.next();
					tab.stop();
				}
			} else {
				ObservableList<Tab> tabs = LogFilesTabPane.getLogFilesTabs(((Node) event.getSource()).getScene());
				for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
					LogFileTab tab = (LogFileTab) iterator.next();
					tab.restart();
				}
			}

		});

	}
}
