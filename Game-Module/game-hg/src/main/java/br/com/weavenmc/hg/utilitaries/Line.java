package br.com.weavenmc.hg.utilitaries;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

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

public final class Line implements Iterable<Block> {
	
	private final String worldName;
	private final Vector start;
	private final Vector end;
	private final Vector direction;
	private final int distance;
	private final double offsetY;

	public Line(Location start, Location end, double offsetY) {
		if (!start.getWorld().getName().equals(end.getWorld().getName()))
			throw new IllegalArgumentException("Cannot create a block line between two different worlds");
		this.worldName = start.getWorld().getName();
		this.start = start.toVector();
		this.end = end.toVector();
		this.direction = this.end.subtract(this.start);
		this.distance = (int) start.distance(end);
		this.offsetY = offsetY;
	}

	public Line(Location start, Location end) {
		this(start, end, 0);
	}

	public Line(LivingEntity start, Location end) {
		this(start.getLocation(), end, start.getEyeHeight());
	}

	public String getWorldName() {
		return this.worldName;
	}

	public World getWorld() throws IllegalStateException {
		World w = Bukkit.getWorld(worldName);
		if (w == null)
			throw new IllegalStateException("World '" + worldName + "' is not loaded");
		return w;
	}

	public Vector getStart() {
		return this.start;
	}

	public Vector getEnd() {
		return this.end;
	}

	public Vector getDirection() {
		return this.direction;
	}

	public int getDistance() {
		return this.distance;
	}

	public double getOffsetY() {
		return this.offsetY;
	}

	@Override
	public Iterator<Block> iterator() {
		return new BlockIterator(getWorld(), start, direction, offsetY, distance);
	}
}
