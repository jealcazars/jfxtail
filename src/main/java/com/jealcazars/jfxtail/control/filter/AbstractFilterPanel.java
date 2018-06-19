package com.jealcazars.jfxtail.control.filter;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public abstract class AbstractFilterPanel<T> extends BorderPane {
	private static final Logger LOG = Logger.getLogger(AbstractFilterPanel.class.getName());

	@FXML
	TableView<T> table;

	@FXML
	CheckBox enabled;

	@FXML
	TextField token;

	@FXML
	ComboBox<String> combo;

	@FXML
	Button saveButton;

	@FXML
	Button deleteButton;

	public AbstractFilterPanel() {
		FXMLViewLoader.load(this, getXmlConfig());

		ObservableList<T> tableData = loadFilters();

		table.setItems(tableData);
		
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			LOG.fine("newSelection: " + newSelection);
			if (newSelection != null) {
				loadFilterToEdit(newSelection);

				deleteButton.setDisable(false);
				saveButton.setDisable(false);
			} else {
				deleteButton.setDisable(true);
				saveButton.setDisable(true);
			}
		});
	}

	@FXML
	private void addFilterToTable(ActionEvent event) {
		if (token.getText() != null && token.getText().trim().length() > 0) {
			LOG.fine("addTextFilterToTable: " + token.getText());
			table.getItems().add(getFiltertoSave());
			token.setText(null);
		}
	}

	@FXML
	private void removeFilterFromTable(ActionEvent event) {
		int index = table.getSelectionModel().getSelectedIndex();
		LOG.fine("Removing: " + index);
		if (index >= 0) {
			token.setText(null);
			table.getItems().remove(index);
		}
	}

	@FXML
	private void saveFilter(ActionEvent event) {
		int index = table.getSelectionModel().getSelectedIndex();
		if (token.getText() != null && token.getText().trim().length() > 0) {
			LOG.fine("addTextFilterToTable: " + token.getText());
			table.getItems().set(index, getFiltertoSave());
		}
	}

	public ObservableList<T> getFilters() {
		return table.getItems();
	}

	public TableView<T> getTable() {
		return table;
	}

	public TextField getToken() {
		return token;
	}

	public ComboBox<String> getCombo() {
		return combo;
	}

	public CheckBox getEnabled() {
		return enabled;
	}

	public abstract T getFiltertoSave();

	public abstract String getXmlConfig();

	public abstract ObservableList<T> loadFilters();

	public abstract void loadFilterToEdit(T filter);

}
