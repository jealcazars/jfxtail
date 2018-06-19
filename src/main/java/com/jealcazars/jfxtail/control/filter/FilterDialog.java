package com.jealcazars.jfxtail.control.filter;

import java.util.LinkedList;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class FilterDialog<T> extends Dialog<LinkedList<T>> {

	AbstractFilterPanel<T> filtersPanel;

	public FilterDialog(String title, AbstractFilterPanel<T> panel) {
		this.filtersPanel = panel;
		getDialogPane().setContent(filtersPanel);
		getDialogPane().setPrefSize(600, 400);

		filtersPanel.prefHeightProperty().bind(getDialogPane().getScene().heightProperty());
		filtersPanel.prefWidthProperty().bind(getDialogPane().getScene().widthProperty());

		setResizable(true);

		setTitle(title);
		setHeaderText(null);

		ButtonType saveButton = new ButtonType("Save", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

		setResultConverter(dialogButton -> {
			if (dialogButton == saveButton) {
				LinkedList<T> values = new LinkedList<T>();
				values.addAll(0, filtersPanel.getFilters());
				return values;
			}
			return null;
		});

	}
}
