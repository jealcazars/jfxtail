package com.jealcazars.jfxtail.control.menu;

import java.util.LinkedList;
import java.util.Optional;
import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.highlight.HighlightFilter;
import com.jealcazars.jfxtail.control.highlight.HighlightFilterPanel;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;

public class HighlightMenuItem extends MenuItem {
	private static final Logger LOG = Logger.getLogger(HighlightMenuItem.class.getName());

	public HighlightMenuItem() {
		setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				LOG.fine("MenuHighlight");

				Dialog<LinkedList<HighlightFilter>> dialog = new Dialog<>();
				HighlightFilterPanel highlightFilterPanel = new HighlightFilterPanel();
				dialog.getDialogPane().setContent(highlightFilterPanel);
				dialog.getDialogPane().setPrefSize(480, 320);

				highlightFilterPanel.prefHeightProperty().bind(dialog.getDialogPane().getScene().heightProperty());
				highlightFilterPanel.prefWidthProperty().bind(dialog.getDialogPane().getScene().widthProperty());

				dialog.setResizable(true);

				dialog.setTitle("Highlight Filters");
				dialog.setHeaderText(null);

				ButtonType saveButton = new ButtonType("Save", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

				dialog.setResultConverter(dialogButton -> {
					if (dialogButton == saveButton) {
						LinkedList<HighlightFilter> values = new LinkedList<HighlightFilter>();
						values.addAll(0, highlightFilterPanel.getHighlightFilters());
						return values;
					}
					return null;
				});

				Optional<LinkedList<HighlightFilter>> optional = dialog.showAndWait();
				optional.ifPresent(result -> {
					JfxTailAppPreferences.saveHighlightFilters(result);
				});
			}
		});
	}

}
