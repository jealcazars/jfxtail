package com.jealcazars.jfxtail.control.menu;

import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.textfilter.TextFilter;
import com.jealcazars.jfxtail.control.textfilter.TextFilterPanel;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;

public class TextFilterMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(TextFilterMenuItem.class.getName());

	public TextFilterMenuItem() {
		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("MenuHighlight");

				Dialog<LinkedList<TextFilter>> dialog = new Dialog<>();
				TextFilterPanel textFiltersPanel = new TextFilterPanel();
				dialog.getDialogPane().setContent(textFiltersPanel);
				dialog.getDialogPane().setPrefSize(480, 320);

				textFiltersPanel.prefHeightProperty().bind(dialog.getDialogPane().getScene().heightProperty());
				textFiltersPanel.prefWidthProperty().bind(dialog.getDialogPane().getScene().widthProperty());

				dialog.setResizable(true);

				dialog.setTitle("Text Filters");
				dialog.setHeaderText(null);

				ButtonType saveButton = new ButtonType("Save", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

				dialog.setResultConverter(dialogButton -> {
					if (dialogButton == saveButton) {
						LinkedList<TextFilter> values = new LinkedList<TextFilter>();
						values.addAll(0, textFiltersPanel.getTextFilters());
						return values;
					}
					return null;
				});

				Optional<LinkedList<TextFilter>> optional = dialog.showAndWait();
				optional.ifPresent(result -> {
					JfxTailAppPreferences.saveTextFilters(result);
				});
			}
		});
	}

}
