package com.jealcazars.jfxtail.control;

import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.scene.layout.VBox;

public class MainToolbar extends VBox {

	public MainToolbar() {
		FXMLViewLoader.load(this, "MainToolBar.xml");
	}

}
