package com.jealcazars.jfxtail.control.filter;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public abstract class AbstractFilterPanel<T> extends BorderPane {
	private static final Logger LOG = Logger.getLogger(AbstractFilterPanel.class.getName());

	@FXML
	TableView<T> table;

	@FXML
	TextField token;

	@FXML
	ComboBox<String> combo;

	public AbstractFilterPanel() {
		FXMLViewLoader.load(this, getXmlConfig());

		ObservableList<T> tableData = loadFilters();

		table.setItems(tableData);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			LOG.fine("newSelection: " + newSelection);
			loadFilterToEdit(newSelection);
		});
	}

	@FXML
	private void addFilterToTable(ActionEvent event) {
		if (token.getText() != null && token.getText().trim().length() > 0) {
			LOG.fine("addTextFilterToTable: " + token.getText());
			table.getItems().add(getFiltertoSave());
		}
	}

	@FXML
	private void removeFilterFromTable(ActionEvent event) {
		int index = table.getSelectionModel().getSelectedIndex();
		LOG.fine("Removing: " + index);
		if (index >= 0) {
			table.getItems().remove(index);
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

	public abstract T getFiltertoSave();

	public abstract String getXmlConfig();

	public abstract ObservableList<T> loadFilters();

	public abstract void loadFilterToEdit(T filter);

}
