package com.jealcazars.jfxtail.control.highlight;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;
import com.jealcazars.jfxtail.view.FXMLViewLoader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class HighlightFilterPanel extends BorderPane {
	private static final Logger LOG = Logger.getLogger(HighlightFilterPanel.class.getName());

	@FXML
	TableView<HighlightFilter> highlightFiltersTable;

	@FXML
	TextField token;

	@FXML
	ComboBox<String> color;

	public HighlightFilterPanel() {
		FXMLViewLoader.load(this, "HighlightFiltersPanel.xml");

		ObservableList<HighlightFilter> tableData = FXCollections
				.observableArrayList(JfxTailAppPreferences.loadHighlightFilters());

		highlightFiltersTable.setItems(tableData);
		highlightFiltersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	@FXML
	private void addHighlightFilterToTable(ActionEvent event) {
		if (token.getText() != null && token.getText().trim().length() > 0) {
			LOG.fine("addHighlightFilterToTable: " + token.getText());
			highlightFiltersTable.getItems()
					.add(new HighlightFilter(token.getText(), color.getSelectionModel().getSelectedItem()));
		}
	}

	@FXML
	private void removeHighlightFilterFromTable(ActionEvent event) {
		int index = highlightFiltersTable.getSelectionModel().getSelectedIndex();
		LOG.fine("Removing: " + index);
		if (index >= 0) {
			highlightFiltersTable.getItems().remove(index);
		}
	}

	public ObservableList<HighlightFilter> getHighlightFilters() {
		return highlightFiltersTable.getItems();
	}

}
