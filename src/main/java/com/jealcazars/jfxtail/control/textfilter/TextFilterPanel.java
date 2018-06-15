package com.jealcazars.jfxtail.control.textfilter;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.control.filter.text.TextFilter;
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

public class TextFilterPanel extends BorderPane {
	private static final Logger LOG = Logger.getLogger(TextFilterPanel.class.getName());

	@FXML
	TableView<TextFilter> textFiltersTable;

	@FXML
	TextField token;

	@FXML
	ComboBox<String> type;

	public TextFilterPanel() {
		FXMLViewLoader.load(this, "TextFiltersPanel.xml");

		ObservableList<TextFilter> tableData = FXCollections
				.observableArrayList(JfxTailAppPreferences.loadTextFilters());

		textFiltersTable.setItems(tableData);
		textFiltersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	@FXML
	private void addTextFilterToTable(ActionEvent event) {
		if (token.getText() != null && token.getText().trim().length() > 0) {
			LOG.fine("addTextFilterToTable: " + token.getText());
			textFiltersTable.getItems()
					.add(new TextFilter(token.getText(), type.getSelectionModel().getSelectedItem()));
		}
	}

	@FXML
	private void removeTextFilterFromTable(ActionEvent event) {
		int index = textFiltersTable.getSelectionModel().getSelectedIndex();
		LOG.fine("Removing: " + index);
		if (index >= 0) {
			textFiltersTable.getItems().remove(index);
		}
	}

	public ObservableList<TextFilter> getTextFilters() {
		return textFiltersTable.getItems();
	}

}
