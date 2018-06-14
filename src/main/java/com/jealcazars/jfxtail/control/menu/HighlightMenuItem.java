package com.jealcazars.jfxtail.control.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class HighlightMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(HighlightMenuItem.class.getName());

	public HighlightMenuItem() {
		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("MenuHighlight");

				// Create the custom dialog.
				Dialog<List<String>> dialog = new Dialog<>();
				dialog.setTitle("Login Dialog");
				dialog.setHeaderText("Look, a Custom Login Dialog");

				// Set the icon (must be included in the project).
				// dialog.setGraphic(new
				// ImageView(this.getClass().getResource("login.png").toString()));

				// Set the button types.
				ButtonType saveButton = new ButtonType("Save", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

				// Create the username and password labels and fields.
				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));

				TextField highlight = new TextField();
				highlight.setPromptText("Username");

				grid.add(highlight, 1, 0);

				dialog.getDialogPane().setContent(grid);

				// Request focus on the username field by default.
				Platform.runLater(() -> highlight.requestFocus());

				// Convert the result to a username-password-pair when the login button is
				// clicked.
				dialog.setResultConverter(dialogButton -> {
					if (dialogButton == saveButton) {
						List<String> values = new ArrayList<>();
						values.add(highlight.getText());
						return values;
					}
					return null;
				});

				Optional<List<String>> optional = dialog.showAndWait();
				optional.ifPresent(result -> {
					System.setProperty("highlight", result.get(0));
				});

			}
		});
	}

}
