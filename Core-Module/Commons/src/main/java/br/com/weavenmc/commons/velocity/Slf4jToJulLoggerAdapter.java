package br.com.weavenmc.commons.velocity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Slf4jToJulLoggerAdapter extends Logger {

	private final org.slf4j.Logger delegate;

	public Slf4jToJulLoggerAdapter(org.slf4j.Logger delegate) {
		super(delegate.getName(), null);
		this.delegate = delegate;
	}

	@Override
	public void log(Level level, String msg) {
		if (level.intValue() >= Level.SEVERE.intValue()) {
			delegate.error(msg);
		} else if (level.intValue() >= Level.WARNING.intValue()) {
			delegate.warn(msg);
		} else {
			delegate.info(msg);
		}
	}

	@Override
	public void log(Level level, String msg, Throwable thrown) {
		if (level.intValue() >= Level.SEVERE.intValue()) {
			delegate.error(msg, thrown);
		} else if (level.intValue() >= Level.WARNING.intValue()) {
			delegate.warn(msg, thrown);
		} else {
			delegate.info(msg, thrown);
		}
	}

	@Override
	public void info(String msg) {
		delegate.info(msg);
	}

	@Override
	public void warning(String msg) {
		delegate.warn(msg);
	}

	@Override
	public void severe(String msg) {
		delegate.error(msg);
	}
}
