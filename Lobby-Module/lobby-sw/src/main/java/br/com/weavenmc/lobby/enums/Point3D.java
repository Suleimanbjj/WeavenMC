package br.com.weavenmc.lobby.enums;

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
public class Point3D {
	
	public float x;
	public float y;
	public float z;

	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Point3D rotate(float rot) {
		double cos = Math.cos(rot);
		double sin = Math.sin(rot);

		return new Point3D((float) (x * cos + z * sin), y, (float) (x * -sin + z * cos));
	}
}