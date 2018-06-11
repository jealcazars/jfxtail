package com.jealcazars.jfxtail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jealcazars.jfxtail.control.MainPanel;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JfxTailApp extends Application {
	private static final Logger LOG = LoggerFactory.getLogger(JfxTailApp.class);

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("jfxTail");
		MainPanel root = new MainPanel();
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add("com/jealcazars/jfxtail/css/jfxtail.css");
		// Screen screen = Screen.getPrimary();
		// Rectangle2D bounds = screen.getVisualBounds();
		//
		// primaryStage.setX(bounds.getMinX());
		// primaryStage.setY(bounds.getMinY());
		// primaryStage.setWidth(bounds.getWidth());
		// primaryStage.setHeight(bounds.getHeight());

		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				LOG.debug("Closing jfxtail");
				System.exit(0);
			}
		});

	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}