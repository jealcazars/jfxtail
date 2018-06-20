package com.jealcazars.jfxtail.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class FXMLViewLoader {
	private static final Logger LOG = Logger.getLogger(JfxTailAppPreferences.class.getName());

	private FXMLViewLoader() {
	}

	public static void load(Node node, String viewXml) {
		FXMLLoader fxmlLoader = new FXMLLoader(FXMLViewLoader.class.getResource(viewXml));
		fxmlLoader.setRoot(node);
		fxmlLoader.setController(node);

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, "Error", e);
		}
	}
}
