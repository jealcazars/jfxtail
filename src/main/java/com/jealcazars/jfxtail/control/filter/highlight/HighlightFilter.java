package com.jealcazars.jfxtail.control.filter.highlight;

import com.jealcazars.jfxtail.control.filter.Filter;

public class HighlightFilter extends Filter {
	String color;

	public HighlightFilter(boolean enabled, String token, String color) {
		super(enabled, token);
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return super.toString() + "HighlightFilter [color=" + color + "]";
	}
}
