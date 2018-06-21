package com.jealcazars.jfxtail.control;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LogFilesTabPane extends TabPane {
	private static final Logger LOG = Logger.getLogger(LogFilesTabPane.class.getName());
	int lastMatchIndex;

	public LogFilesTabPane() {
		super();
		List<String> files = JfxTailAppPreferences.getOpenFiles();

		for (Iterator<String> iterator = files.iterator(); iterator.hasNext();) {
			File file = new File(iterator.next());
			if (file.exists()) {
				addFile(file, false);
			} else {
				LOG.fine("File doesnt exists" + file.getAbsolutePath());
			}
		}

		getSelectionModel().selectedItemProperty().addListener((event, oldValue, newValue) -> {
			if (newValue != null) {
				newValue.setGraphic(null);
			}
		});
	}

	public void addFile(File file, boolean setLastKnowFolder) {
		if (!isAlreadyOpen(file.getAbsolutePath())) {
			LOG.fine("Adding file to panel: " + file.getAbsolutePath());
			LogFileTab tabFile = new LogFileTab(file);

			getTabs().add(tabFile);

			if (setLastKnowFolder) {
				JfxTailAppPreferences.setLastKnowFolder(file.getParentFile().getAbsolutePath());
			}

			JfxTailAppPreferences.addToLastOpenFiles(file.getAbsolutePath());
		} else {
			LOG.fine("File already open");
			Alert alert = new Alert(AlertType.NONE, "File is already open ", ButtonType.OK);
			alert.setTitle("Warning");
			alert.showAndWait();
		}
	}

	private boolean isAlreadyOpen(String absolutePath) {
		Iterator<Tab> tabIt = getTabs().iterator();
		while (tabIt.hasNext()) {
			LogFileTab tab = (LogFileTab) tabIt.next();
			if (tab.getFilePath().equals(absolutePath)) {
				return true;
			}
		}
		return false;
	}

	public void executeSearch() {
		LogFileTab selectedtab = (LogFileTab) getSelectionModel().getSelectedItem();

		if (selectedtab != null && JfxTailAppPreferences.getLastSearch().trim().length() > 0) {
			Matcher matcher = Pattern.compile(JfxTailAppPreferences.getLastSearch(),
					(JfxTailAppPreferences.isLastSearchMatchCase() ? 0x00 : Pattern.CASE_INSENSITIVE)
							| Pattern.UNICODE_CASE)
					.matcher(selectedtab.getCodeArea().getText());
			executeSearch(matcher, selectedtab, selectedtab.getCodeArea().getCaretPosition());
		}
	}

	private void executeSearch(Matcher matcher, LogFileTab selectedtab, int startIndex) {
		if (LOG.isLoggable(Level.FINE)) {
			LOG.fine("executeSearch found {}" + matcher + " startIndex:" + startIndex);
		}

		if (matcher.find(startIndex)) {
			LOG.fine(" found ");
			selectedtab.getCodeArea().selectRange(matcher.start(), matcher.end());
			lastMatchIndex = matcher.end();
		} else if (startIndex > 0) {
			LOG.fine(" not found,starting from the 0 index ");
			executeSearch(matcher, selectedtab, 0);
		} else {
			LOG.fine(" not found ");
			selectedtab.getCodeArea().selectRange(selectedtab.getCodeArea().getCaretPosition(),
					selectedtab.getCodeArea().getCaretPosition());
			Alert dialog = new Alert(AlertType.NONE, "Not found", ButtonType.CLOSE);
			dialog.setTitle("Search");
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(e -> ((Stage) window).close());
			dialog.showAndWait();
		}
	}

	public static LogFilesTabPane getLogFilesTabPane(Scene scene) {
		return (LogFilesTabPane) scene.lookup("#logFilesTabPane");
	}

	public static ObservableList<Tab> getLogFilesTabs(Scene scene) {
		TabPane tabPane = (TabPane) scene.lookup("#logFilesTabPane");
		return tabPane.getTabs();
	}

}
