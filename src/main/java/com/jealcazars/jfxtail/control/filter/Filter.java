package com.jealcazars.jfxtail.control.filter;

public class Filter {

	private boolean enabled;
	private String token;

	public Filter(boolean enabled, String token) {
		super();
		this.enabled = enabled;
		this.token = token;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Filter [enabled=" + enabled + ", token=" + token + "]";
	}

}
