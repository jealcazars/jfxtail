package com.jealcazars.jfxtail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jealcazars.jfxtail.control.MainPanel;
import com.jealcazars.jfxtail.control.TabFile;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JfxTailApp extends Application {
	private static final Logger LOG = LoggerFactory.getLogger(JfxTailApp.class);

	@Override
	public void start(Stage primaryStage) throws Exception {
		Thread thread = new Thread() {
			public void run() {
				File file = new File("d://prueba.log");
				if (file.exists()) {
					file.delete();
				}

				int i = 0;
				while (true) {

					try {
						Thread.sleep(2000L);
						PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("d://prueba.log", true)));
						out.println("line " + i + " " + new Date());
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					i++;
				}
			}
		};

		thread.start();

		primaryStage.setTitle("jfxTail");
		MainPanel root = new MainPanel(primaryStage);
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
				System.exit(0);
			}

		});

	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}