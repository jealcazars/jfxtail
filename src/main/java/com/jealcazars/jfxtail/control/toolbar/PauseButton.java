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

public class PauseButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(PauseButton.class.getName());

	boolean active = false;

	public PauseButton() {

		setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				active = !active;
				LOG.fine("PauseButtonClick active " + active);

				if (active) {
					TabPane tabPane = (TabPane) ((Node) event.getSource()).getScene().lookup("#logFilesTabPane");
					ObservableList<Tab> tabs = tabPane.getTabs();
					for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
						LogFileTab tab = (LogFileTab) iterator.next();
						tab.stop();
					}
				} else {
					TabPane tabPane = (TabPane) ((Node) event.getSource()).getScene().lookup("#logFilesTabPane");
					ObservableList<Tab> tabs = tabPane.getTabs();
					for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
						LogFileTab tab = (LogFileTab) iterator.next();
						tab.restart();
					}
				}
			}
		});

	}
}
