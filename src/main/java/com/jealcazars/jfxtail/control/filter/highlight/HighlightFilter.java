package com.jealcazars.jfxtail.control.filter.highlight;

public class HighlightFilter {
	private String token;
	String color;

	public HighlightFilter(String token, String color) {
		super();
		this.token = token;
		this.color = color;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "HighlightFilter [token=" + token + ", color=" + color + "]";
	}

}
