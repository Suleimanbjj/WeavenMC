package br.com.weavenmc.hg;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Preconditions;

import br.com.weavenmc.hg.logger.Logger;
import br.com.weavenmc.hg.manager.Manager;
import br.com.weavenmc.hg.schematic.DataException;
import br.com.weavenmc.hg.schematic.Schematic;
import br.com.weavenmc.commons.bukkit.command.BukkitCommandFramework;
import br.com.weavenmc.commons.core.command.CommandLoader;

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

public class HungerGames extends JavaPlugin {

	private static Manager manager;
	private boolean TEST_SERVER;

	public static final ArrayList<Block> coliseu = new ArrayList<>();
	private static Location spawn = null;

	private static Logger logger;

	public void onEnable() {
		super.onEnable();

		manager = new Manager(this);

		Schematic s;

		logger.log("Loading the coliseum file...");
		try {
			s = Schematic.getInstance().loadSchematic(new File(getDataFolder(), "coliseu.schematic"));
		} catch (DataException | IOException e) {
			e.printStackTrace();
			getServer().shutdown();
			return;
		}
		logger.log("Coliseum file loaded!");

		if (s != null) {
			logger.log("Spawning the coliseu...");
			Schematic.getInstance().newSchematic(getServer().getWorlds().get(0),
					new Location(getServer().getWorlds().get(0), -27.5D, 80.0D, -27.5D), s, coliseu);
			logger.log("Coliseum spawned!");
			findSpawn();
		}

		new CommandLoader(new BukkitCommandFramework(this))
				.loadCommandsFromPackage("br.com.weavenmc.hg.commands");
	}

	public void onDisable() {
		super.onDisable();
	}

	@Override
	public File getFile() {
		return super.getFile();
	}

	public void onLoad() {
		super.onLoad();

		logger = new Logger(getLogger(), null, true);
		logger.log("Starting to load the plugin.");

		TEST_SERVER = getConfig().getBoolean("test_server");
		if (!TEST_SERVER) {
			getServer().unloadWorld("world", false);
			deleteDir(new File("world"));

			String worldName = "arena";
			File file = new File(worldName);
			deleteDir(file);

			String worldName2 = "arena_bk";
			File file2 = new File(worldName2);

			copyFolder(file2, file);

			for (World world : Bukkit.getWorlds()) {
				world.setThundering(false);
				world.setStorm(false);
				world.setAutoSave(false);
				world.setTime(0L);
			}
		}
	}

	public boolean copyFolder(File src, File dest) {
		return false;
	}

	public boolean deleteFile(Path path) {
		Preconditions.checkNotNull(path);
		try {
			Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				public FileVisitResult visitFileFailed(Path file, IOException e) {
					return handleException(e);
				}

				private FileVisitResult handleException(IOException e) {
					e.printStackTrace();
					return FileVisitResult.TERMINATE;
				}

				public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
					if (e != null)
						return handleException(e);
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
			return true;
		} catch (IOException e) {
			getManager().getLogger().error("Error when the plugin is trying to delete the path " + path, e);
			return false;
		}
	}

	public void findSpawn() {
		if (spawn == null) {
			for (Iterator<?> i = coliseu.iterator(); i.hasNext();) {
				Block b = (Block) i.next();
				if (b.getType() == Material.EMERALD_BLOCK) {
					spawn = new Location(b.getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY(),
							b.getLocation().getBlockZ()).add(0.0D, 4.0D, 0.0D);
					return;
				}
			}
			if (spawn == null)
				spawn = Bukkit.getWorld("world").getSpawnLocation();
			for (int i = 0; i < 4; i++) {
				Block block = spawn.add(i, i, i).getBlock();
				Chunk chunk = block.getChunk();
				chunk.load(true);

				block = spawn.add(-i, -i, -i).getBlock();
				chunk = block.getChunk();
				chunk.load(true);
			}
		}
	}

	public static void teleportSpawn(Player player) {
		player.teleport(spawn);
	}

	public static void removeColiseu() {
		for (Iterator<?> i = coliseu.iterator(); i.hasNext();) {
			Block b = (Block) i.next();
			if (b.getType() != Material.AIR) {
				getManager().getBO2().setBlockFast(b.getLocation(), Material.AIR, (byte) 0);
			}
		}
		coliseu.clear();
	}

	@SuppressWarnings("deprecation")
	public static void openColiseu() {
		for (Iterator<?> i = coliseu.iterator(); i.hasNext();) {
			Block b = (Block) i.next();
			if (b.getType() == Material.getMaterial(33)) {
				getManager().getBO2().setBlockFast(b.getLocation(), Material.AIR, (byte) 0);
			}
		}
	}

	public boolean TEST_SERVER() {
		return TEST_SERVER;
	}

	public static Manager getManager() {
		return manager;
	}

	public Logger getLoggerSecon() {
		return logger;
	}

	public void loadChunks() {
		World world = Bukkit.getWorld("world");
		for (int x = -532; x <= 532; x += 16) {
			for (int z = -532; z <= 532; z += 16) {
				world.getBlockAt(x, 64, z).getChunk().load(true);
			}
		}
	}

	public void deleteDir(File file) {
		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				deleteDir(new File(file, children[i]));
			}
		}
		file.delete();
	}

}
