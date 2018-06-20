package com.jealcazars.jfxtail.control.filter.text;

import com.jealcazars.jfxtail.control.filter.AbstractFilterPanel;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn.CellEditEvent;

public class TextFilterPanel extends AbstractFilterPanel<TextFilter> {

	@Override
	public String getXmlConfig() {
		return "TextFiltersPanel.xml";
	}

	@Override
	public ObservableList<TextFilter> loadFilters() {
		return FXCollections.observableArrayList(JfxTailAppPreferences.loadTextFilters());
	}

	@Override
	public TextFilter addNewFilter() {
		return new TextFilter(false, "-", "Include");
	}

	@Override
	public void editComboCell(CellEditEvent<?, ?> event) {
		getTable().getItems().get(event.getTablePosition().getRow()).setType((String) event.getNewValue());
	}

}
