package com.jealcazars.jfxtail.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class FXMLViewLoader
{
	public static void load(Node node, String viewXml)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(FXMLViewLoader.class.getResource(viewXml));
		fxmlLoader.setRoot(node);
		fxmlLoader.setController(node);

		try
		{
			fxmlLoader.load();
		} catch (IOException exception)
		{
			throw new RuntimeException(exception);
		}
	}
}
