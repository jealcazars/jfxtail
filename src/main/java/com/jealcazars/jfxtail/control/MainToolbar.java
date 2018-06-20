package com.jealcazars.jfxtail.control;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.toolbar.FilterButton;
import com.jealcazars.jfxtail.control.toolbar.FollowTailButton;
import com.jealcazars.jfxtail.control.toolbar.HighlightButton;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;
import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainToolbar extends VBox {
	private static final Logger LOG = Logger.getLogger(MainToolbar.class.getName());

	@FXML
	FilterButton filterButton;

	@FXML
	HighlightButton highlightButton;

	@FXML
	FollowTailButton followTailButton;

	public MainToolbar() {
		FXMLViewLoader.load(this, "MainToolBar.xml");
		LOG.fine("JfxTailAppPreferences.isFilterActive " + JfxTailAppPreferences.isFilterActive());
		LOG.fine("JfxTailAppPreferences.isHighlightActive " + JfxTailAppPreferences.isHighlightActive());
		LOG.fine("JfxTailAppPreferences.isFollowTailActive " + JfxTailAppPreferences.isFollowTailActive());

		filterButton.setSelected(JfxTailAppPreferences.isFilterActive());
		highlightButton.setSelected(JfxTailAppPreferences.isHighlightActive());
		followTailButton.setSelected(JfxTailAppPreferences.isFollowTailActive());
	}

}
