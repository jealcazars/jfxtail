package com.jealcazars.jfxtail.control.toolbar;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jealcazars.jfxtail.control.TabFile;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PlayButton extends Button {
	private static final Logger LOG = LoggerFactory.getLogger(PauseButton.class);

	public PlayButton() {
		this.setDisable(true);

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.debug("PlayButton");
				PlayButton playButton = (PlayButton) event.getSource();
				Scene scene = playButton.getScene();
				PauseButton pauseButton = (PauseButton) scene.lookup("#pauseButton");
				playButton.setDisable(true);
				pauseButton.setDisable(false);

				TabPane tabPane = (TabPane) ((Node) event.getSource()).getScene().lookup("#tabPane");
				ObservableList<Tab> tabs = tabPane.getTabs();
				for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
					TabFile tab = (TabFile) iterator.next();
					tab.restart();
				}
			}
		});
	}
}
