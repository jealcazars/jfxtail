package com.jealcazars.jfxtail.control.menu;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

public class MaxLineMenuItem extends AbstractIntDialogMenuItem {

	public MaxLineMenuItem() {
		super("Max. Lines Number", "Max. Lines Number:");
	}

	@Override
	public void save(Integer result) {
		JfxTailAppPreferences.setMaxLines(result);
	}

	@Override
	public Integer getDefaultValue() {
		return JfxTailAppPreferences.getMaxLines();
	}

}
