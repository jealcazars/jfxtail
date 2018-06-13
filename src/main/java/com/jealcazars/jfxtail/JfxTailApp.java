package com.jealcazars.jfxtail;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.MainPanel;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JfxTailApp extends Application {
	private static final Logger LOG = Logger.getLogger(JfxTailApp.class.getName());

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] [%4$-6s] %2$s %5$s%6$s%n");

		Logger rootLog = Logger.getLogger("");
		rootLog.setLevel(Level.FINE);
		rootLog.getHandlers()[0].setLevel(Level.FINE);

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
				LOG.fine("Closing jfxtail");
				System.exit(0);
			}
		});

	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}