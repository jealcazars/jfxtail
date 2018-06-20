package com.jealcazars.jfxtail.control.filter;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public abstract class AbstractFilterPanel<T> extends BorderPane {
	private static final Logger LOG = Logger.getLogger(AbstractFilterPanel.class.getName());

	@FXML
	TableView<T> table;

	@FXML
	Button deleteButton;

	public AbstractFilterPanel() {
		FXMLViewLoader.load(this, getXmlConfig());

		ObservableList<T> tableData = loadFilters();

		table.setItems(tableData);

		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			LOG.fine("newSelection: " + newSelection);
			if (newSelection != null) {
				deleteButton.setDisable(false);
			} else {
				deleteButton.setDisable(true);
			}
		});
	}

	@FXML
	private void addFilterToTable(ActionEvent event) {
		table.getItems().add(addNewFilter());
	}

	@FXML
	private void removeFilterFromTable(ActionEvent event) {
		int index = table.getSelectionModel().getSelectedIndex();
		LOG.fine("Removing: " + index);
		if (index >= 0) {
			table.getItems().remove(index);
		}
	}

	@FXML
	private void editTokenCell(CellEditEvent<?, ?> event) {
		((Filter) table.getItems().get(event.getTablePosition().getRow())).setToken((String) event.getNewValue());
	}

	@FXML
	private void editEnabledCell(CellEditEvent<?, ?> event) {
		((Filter) table.getItems().get(event.getTablePosition().getRow()))
				.setEnabled(Boolean.valueOf(event.getNewValue().toString()));
	}

	@FXML
	public abstract void editComboCell(CellEditEvent<?, ?> event);

	public ObservableList<T> getFilters() {
		return table.getItems();
	}

	public TableView<T> getTable() {
		return table;
	}

	public abstract T addNewFilter();

	public abstract String getXmlConfig();

	public abstract ObservableList<T> loadFilters();

}
