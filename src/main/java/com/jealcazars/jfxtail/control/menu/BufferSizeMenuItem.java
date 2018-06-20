package com.jealcazars.jfxtail.control.menu;

import java.util.logging.Logger;

import com.jealcazars.jfxtail.utils.JfxTailAppPreferences;

public class BufferSizeMenuItem extends AbstractIntDialogMenuItem {
	private static final Logger LOG = Logger.getLogger(BufferSizeMenuItem.class.getName());

	public BufferSizeMenuItem() {
		super("Buffer Size", "Buffer Size in bytes (1000000 bytes = 1 mb):");
	}

	@Override
	public void save(Integer result) {
		LOG.fine("BufferSizeMenuItem new value:" + result);
		JfxTailAppPreferences.setBufferSize(result);
	}

	@Override
	public Integer getDefaultValue() {
		return JfxTailAppPreferences.getBufferSize();
	}

}
