package com.jealcazars.jfxtail.control.filter.text;

import com.jealcazars.jfxtail.control.filter.AbstractFilterPanel;
import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	public TextFilter getFiltertoSave() {
		return new TextFilter(getToken().getText(), getCombo().getSelectionModel().getSelectedItem());
	}

	@Override
	public void loadFilterToEdit(TextFilter filter) {
		getToken().setText(filter.getToken());
		getCombo().getSelectionModel().select(filter.getType());
	}

}
