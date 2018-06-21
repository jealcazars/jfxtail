package com.jealcazars.jfxtail.control.toolbar;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.LogFilesTabPane;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;
import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SearchButton extends Button {
	private static final Logger LOG = Logger.getLogger(SearchButton.class.getName());

	public SearchButton() {

		setOnAction(event -> {
			LOG.fine("SearchButton");

			Alert dialog = new Alert(AlertType.NONE, "File is already open ");
			dialog.setTitle("Search");
			dialog.getDialogPane().setPrefSize(250, 100);
			dialog.setResizable(true);

			SearchPanel panel = new SearchPanel(getScene());
			dialog.getDialogPane().setContent(panel);
			panel.prefHeightProperty().bind(dialog.getDialogPane().getScene().heightProperty());
			panel.prefWidthProperty().bind(dialog.getDialogPane().getScene().widthProperty());

			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(e -> {
				panel.saveStatus();
				((Stage) window).close();
			});

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

		public SearchPanel(Scene scene) {
			super();
			FXMLViewLoader.load(this, "SearchPanel.xml");
			text.setText(JfxTailAppPreferences.getLastSearch());
			caseCheckBox.setSelected(JfxTailAppPreferences.isLastSearchMatchCase());

			text.textProperty().addListener((observable, oldValue, newValue) -> {
				searchButton.setDisable(text.getText() != null && text.getText().trim().length() == 0);
				saveStatus();
			});

			caseCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
				saveStatus();
			});

			this.parentScene = scene;

		}

		@FXML
		private void closeDialog(ActionEvent event) {
			LOG.fine("Closing search dialog");
			saveStatus();
			((Stage) getScene().getWindow()).close();
		}

		private void saveStatus() {
			JfxTailAppPreferences.setLastSearch(text.getText());
			JfxTailAppPreferences.setLastSearchMatchCase(caseCheckBox.isSelected());
		}

		@FXML
		private void search(ActionEvent event) {
			LOG.fine("search");
			LogFilesTabPane tabPane = LogFilesTabPane.getLogFilesTabPane(parentScene);
			tabPane.executeSearch();
		}

	}
}
