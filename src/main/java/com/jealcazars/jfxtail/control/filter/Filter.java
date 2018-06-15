package com.jealcazars.jfxtail.control.filter;

public class Filter {
	private String token;

	public Filter(String token) {
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
		return "Filter [token=" + token + "]";
	}

}
