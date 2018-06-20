package com.jealcazars.jfxtail.control.filter;

import javafx.beans.property.SimpleBooleanProperty;

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

	public SimpleBooleanProperty enabledProperty() {
		return new SimpleBooleanProperty(enabled);
	}

	@Override
	public String toString() {
		return "Filter [enabled=" + enabled + ", token=" + token + "]";
	}

}
