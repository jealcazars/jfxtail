package com.jealcazars.jfxtail.control.filter.text;

import com.jealcazars.jfxtail.control.filter.Filter;

public class TextFilter extends Filter {

	private String type;

	public static final String TYPE_INCLUDE = "Include";
	public static final String TYPE_EXCLUDE = "Exclude";

	public TextFilter(boolean enabled, String token, String type) {
		super(enabled, token);
		this.type = type;
	}

	public String getType() {
		return type != null ? type : TYPE_INCLUDE;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return super.toString() + "TextFilter [type=" + type + "]";
	}

}
