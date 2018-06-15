package com.jealcazars.jfxtail.control.toolbar;

import java.util.Iterator;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;

public class HighlightButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(HighlightButton.class.getName());
	boolean active = false;

	public HighlightButton() {

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (active) {
					System.setProperty("CleanHighlights", "true");
					LOG.fine("Highlights must be cleaned");
				} else {
					System.setProperty("CleanHighlights", "false");
				}

				active = !active;

				TabPane tabPane = (TabPane) ((Node) event.getSource()).getScene().lookup("#logFilesTabPane");
				ObservableList<Tab> tabs = tabPane.getTabs();
				for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
					LogFileTab tab = (LogFileTab) iterator.next();

					if (active) {
						tab.applyHighlightFilter();
					} else {
						tab.cleanHighlightFilter();
					}
				}

				LOG.fine("HighlightButton -- Not implemented yet");
				System.setProperty("HighlightButton", String.valueOf(active));
			}
		});

	}
}
