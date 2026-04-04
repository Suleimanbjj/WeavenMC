package br.com.weavenmc.commons.velocity.checker;

import java.util.concurrent.atomic.AtomicBoolean;
/**
 * Copyright (C) WeavenMC. All Rights Reserved.
 *
 * Unauthorized copying, modification, distribution, or use of this file,
 * via any medium, is strictly prohibited without explicit permission
 * from the copyright holder.
 *
 * Distribution and use for educational purposes are permitted.
 * Commercial use, resale, or any form of monetized distribution
 * is strictly prohibited under GNU License terms and applicable
 * copyright laws.
 *
 * Violators may be subject to legal action.
 */

public class CheckingConnection {

	private String ipAddress;
	private Exception checkError;
	
	public boolean saved = false;
	private boolean checked, allowed, pinged;
	private AtomicBoolean checking;
	private long connectionThrolled = 0L;
	
	public CheckingConnection(String ipAddress) {
		this.ipAddress = ipAddress;
		checkError = null;
		checked = false;
		allowed = false;
		checking = new AtomicBoolean();
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public boolean hasPinged() {
		return pinged;
	}
	
	public void ping() {
		pinged = true;
	}
	
	public void error(Exception exception) {
		this.checkError = exception;
	}
	
	public void setChecked(boolean value) {
		checked = value;
	}
	
	public boolean hasError() {
		return checkError != null;
	}
	
	public Exception getError() {
		return checkError;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void allow() {
		allowed = true;
	}
	
	public void disallow() {
		allowed = false;
	}
	
	public boolean isAllowed() {
		return allowed;
	}
	
	public void initiCheck() {
		checking.set(true);
		connectionThrolled = (20 * 1000L) + System.currentTimeMillis();
	}
	
	public void setChecking(boolean value) {
		checking.set(value);
	}
	
	public void fullyChecked() {
		connectionThrolled = 0L;
		checking.set(false);
	}
	
	public boolean isChecking() {
		return checking.get() || connectionThrolled >= System.currentTimeMillis();
	}
}
