package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.scene.control.ToggleButton;

public class FollowTailButton extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(FollowTailButton.class.getName());

	public FollowTailButton() {
		setOnAction(event -> {
			LOG.fine("FollowTailButton active: " + isSelected());
			JfxTailAppPreferences.setFollowTail(isSelected());
		});
	}
}
