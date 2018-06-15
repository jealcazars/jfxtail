package com.jealcazars.jfxtail.control.textfilter;

public class TextFilter {
	private String token;

	public TextFilter(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TextFilter [token=" + token + "]";
	}

}
