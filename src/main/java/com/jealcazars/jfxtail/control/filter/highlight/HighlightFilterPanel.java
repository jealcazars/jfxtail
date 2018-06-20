package com.jealcazars.jfxtail.control.filter.highlight;

import com.jealcazars.jfxtail.control.filter.AbstractFilterPanel;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn.CellEditEvent;

public class HighlightFilterPanel extends AbstractFilterPanel<HighlightFilter> {

	@Override
	public String getXmlConfig() {
		return "HighlightFiltersPanel.xml";
	}

	@Override
	public ObservableList<HighlightFilter> loadFilters() {
		return FXCollections.observableArrayList(JfxTailAppPreferences.loadHighlightFilters());
	}

	@Override
	public HighlightFilter addNewFilter() {
		return new HighlightFilter(false, "-", "");
	}

	@Override
	public void editComboCell(CellEditEvent<?, ?> event) {
		getTable().getItems().get(event.getTablePosition().getRow()).setColor((String) event.getNewValue());
	}
}
