package com.jealcazars.jfxtail.control.toolbar;

import java.util.Iterator;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;
import com.jealcazars.jfxtail.control.LogFilesTabPane;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;

public class HighlightButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(HighlightButton.class.getName());

	public HighlightButton() {

		setOnAction(event -> {
			LOG.fine("Highlight Filter active: " + isSelected());
			JfxTailAppPreferences.setHighlightActive(isSelected());
			ObservableList<Tab> tabs = LogFilesTabPane.getLogFilesTabs(((Node) event.getSource()).getScene());
			for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
				LogFileTab tab = (LogFileTab) iterator.next();

				if (isSelected()) {
					tab.applyHighlightFilter();
				} else {
					tab.cleanHighlightFilter();
				}
			}
		});
	}

}
