package br.com.weavenmc.commons.core.data.player;

import java.util.ArrayList;
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

public class Data {

	private Object data;

	public Data(Object data){
		this.data = data;
	}
	
	public void setValue(Object data) {
		this.data = data;
	}

	public Object asObject() {
		return data;
	}

	public String asString() {
		return (String) data;
	}

	public Integer asInt() {
		return (Integer) data;
	}

	public Long asLong() {
		return (Long) data;
	}

	public Boolean asBoolean() {
		return (Boolean) data;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<String> asList() {
		return (ArrayList<String>) data;
	}
	
	@Override
	public String toString() {
		if (data != null)
			return data.toString();
		return "null";
	}
}
