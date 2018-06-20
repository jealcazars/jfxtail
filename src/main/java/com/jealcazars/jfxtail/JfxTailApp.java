package com.jealcazars.jfxtail;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFileTab;
import com.jealcazars.jfxtail.control.LogFilesTabPane;
import com.jealcazars.jfxtail.control.MainPanel;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JfxTailApp extends Application {
	private static final Logger LOG = Logger.getLogger(JfxTailApp.class.getName());

	@Override
	public void start(Stage primaryStage) {
		setLoggerConfig();

		LOG.fine("Starting JfxTailApp");

		primaryStage.setTitle("jfxTail");
		MainPanel root = new MainPanel();
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add("com/jealcazars/jfxtail/css/jfxtail.css");
		primaryStage.setScene(scene);

		scene.setOnDragOver(event -> {
			Dragboard db = event.getDragboard();
			if (db.hasFiles()) {
				event.acceptTransferModes(TransferMode.LINK);
			} else {
				event.consume();
			}
		});

		// Dropping over surface
		scene.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasFiles()) {
				success = true;
				for (File file : db.getFiles()) {
					LOG.fine("Drag&Drop adding: " + file.getAbsolutePath());

					LogFilesTabPane tabPaneFiles = (LogFilesTabPane) scene.lookup("#logFilesTabPane");
					tabPaneFiles.addFile(file, false);
				}
				LOG.fine("Files added, refreshing recent menu now");
			}

			event.setDropCompleted(success);
			event.consume();
		});

		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				LOG.fine("Closing jfxtail");

				TabPane tabPane = (TabPane) scene.lookup("#logFilesTabPane");
				ObservableList<Tab> tabs = tabPane.getTabs();

				List<String> files = new LinkedList<>();
				for (Iterator<Tab> iterator = tabs.iterator(); iterator.hasNext();) {
					files.add(((LogFileTab) iterator.next()).getFilePath());
				}

				JfxTailAppPreferences.saveOpenFiles(files);

				System.exit(0);
			}
		});
	}

	private void setLoggerConfig() {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] [%4$-6s] %2$s %5$s%6$s%n");

		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.FINE);
		Logger logger = Logger.getLogger("com.jealcazars");
		logger.addHandler(consoleHandler);
		logger.setLevel(Level.FINE);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}