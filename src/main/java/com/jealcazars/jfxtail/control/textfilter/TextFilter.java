package com.jealcazars.jfxtail.control.textfilter;

public class TextFilter {

	private String token;
	private String type;

	public static final String TYPE_INCLUDE = "Include";
	public static final String TYPE_EXCLUDE = "Exclude";

	public TextFilter(String token, String type) {
		super();
		this.token = token;
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type != null ? type : TYPE_INCLUDE;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TextFilter [token=" + token + ", type=" + type + "]";
	}

}
