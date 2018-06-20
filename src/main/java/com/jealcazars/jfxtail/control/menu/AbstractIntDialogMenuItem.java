package com.jealcazars.jfxtail.control.menu;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public abstract class AbstractIntDialogMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(AbstractIntDialogMenuItem.class.getName());

	public AbstractIntDialogMenuItem(String title, String labelText) {

		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine(getClass().getName());
				Dialog<Integer> dialog = new Dialog<>();

				VBox vBox = new VBox();

				dialog.getDialogPane().setContent(vBox);
				dialog.getDialogPane().setPrefSize(250, 100);
				dialog.setResizable(true);
				dialog.setTitle(title);
				dialog.setHeaderText(null);

				vBox.prefHeightProperty().bind(dialog.getDialogPane().getScene().heightProperty());
				vBox.prefWidthProperty().bind(dialog.getDialogPane().getScene().widthProperty());

				ButtonType saveButton = new ButtonType("Save", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);
				final Label label = new Label(labelText);

				final TextField numericTextField = new TextField();
				numericTextField.setAlignment(Pos.CENTER_RIGHT);
				numericTextField.setText(getDefaultValue().toString());

				numericTextField.textProperty().addListener(new ChangeListener<String>() {
					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						if (!newValue.matches("\\d*")) {
							numericTextField.setText(newValue.replaceAll("[^\\d]", ""));
						}

						if (numericTextField.getText().trim().length() == 0) {
							dialog.getDialogPane().lookupButton(saveButton).setDisable(true);
						}
					}
				});

				vBox.getChildren().addAll(label, numericTextField);

				dialog.setResultConverter(dialogButton -> {
					if (dialogButton == saveButton) {
						return Integer.parseInt(numericTextField.getText());
					}
					return getDefaultValue();
				});

				Optional<Integer> optional = dialog.showAndWait();
				optional.ifPresent(result -> {
					save(result);
				});

			}
		});
	}

	public abstract Integer getDefaultValue();

	public abstract void save(Integer result);
}
