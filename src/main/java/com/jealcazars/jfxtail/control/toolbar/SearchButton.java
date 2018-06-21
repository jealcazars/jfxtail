package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jealcazars.jfxtail.control.LogFileTab;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;
import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SearchButton extends Button {
	private static final Logger LOG = Logger.getLogger(SearchButton.class.getName());

	public SearchButton() {

		setOnAction(event -> {
			LOG.fine("SearchButton -- Not implemented yet");

			Alert dialog = new Alert(AlertType.NONE, "File is already open ");
			dialog.setTitle("Search");
			dialog.getDialogPane().setPrefSize(250, 100);

			dialog.setResizable(true);
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(e -> ((Stage) window).close());

			SearchPanel panel = new SearchPanel(getScene());
			dialog.getDialogPane().setContent(panel);
			panel.prefHeightProperty().bind(dialog.getDialogPane().getScene().heightProperty());
			panel.prefWidthProperty().bind(dialog.getDialogPane().getScene().widthProperty());

			dialog.showAndWait();
		});

	}

	class SearchPanel extends GridPane {

		@FXML
		Button searchButton;

		@FXML
		TextField text;

		@FXML
		CheckBox caseCheckBox;

		Scene parentScene;

		int lastMatchIndex;

		public SearchPanel(Scene scene) {
			super();
			FXMLViewLoader.load(this, "SearchPanel.xml");
			text.textProperty().addListener((observable, oldValue, newValue) -> searchButton
					.setDisable(text.getText() != null && text.getText().trim().length() == 0));
			this.parentScene = scene;

			text.setText(JfxTailAppPreferences.getLastSearch());
		}

		@FXML
		private void closeDialog(ActionEvent event) {
			LOG.fine("Closing search dialog");
			((Stage) getScene().getWindow()).close();
		}

		@FXML
		private void search(ActionEvent event) {
			LOG.fine("search");
			TabPane tabPane = (TabPane) parentScene.lookup("#logFilesTabPane");
			LogFileTab selectedtab = (LogFileTab) tabPane.getSelectionModel().getSelectedItem();

			if (selectedtab != null) {
				Matcher matcher = Pattern
						.compile(text.getText(),
								(caseCheckBox.isSelected() ? 0x00 : Pattern.CASE_INSENSITIVE) | Pattern.UNICODE_CASE)
						.matcher(selectedtab.getCodeArea().getText());
				executeSearch(matcher, selectedtab, selectedtab.getCodeArea().getCaretPosition());
			}

			JfxTailAppPreferences.setLastSearch(text.getText());
		}

		private void executeSearch(Matcher matcher, LogFileTab selectedtab, int startIndex) {
			if (matcher.find(startIndex)) {
				LOG.fine(text.getText() + " found ");
				selectedtab.getCodeArea().selectRange(matcher.start(), matcher.end());
				lastMatchIndex = matcher.end();
			} else if (startIndex > 0) {
				LOG.fine(text.getText() + " not found,starting from the 0 index ");
				executeSearch(matcher, selectedtab, 0);
			} else {
				LOG.fine(text.getText() + " not found ");
				selectedtab.getCodeArea().selectRange(0, 0);
				Alert dialog = new Alert(AlertType.NONE, "Not found", ButtonType.CLOSE);
				dialog.setTitle("Search");
				Window window = dialog.getDialogPane().getScene().getWindow();
				window.setOnCloseRequest(e -> ((Stage) window).close());
				dialog.showAndWait();
			}
		}
	}
}
