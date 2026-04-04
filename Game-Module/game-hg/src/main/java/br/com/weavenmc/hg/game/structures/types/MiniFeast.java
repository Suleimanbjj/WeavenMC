package br.com.weavenmc.hg.game.structures.types;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.weavenmc.hg.bo2.BO2Constructor.FutureBlock;
import br.com.weavenmc.hg.game.structures.Structure;
import br.com.weavenmc.hg.manager.Manager;
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

@SuppressWarnings("deprecation")
public class MiniFeast extends Structure {

	private int x, z, y, size;
	private ArrayList<Block> chestsData;
	private ItemStack[] feastStacks = { new ItemStack(Material.DIAMOND_SWORD), new ItemStack(Material.FLINT_AND_STEEL),
			new ItemStack(Material.WATER_BUCKET), new ItemStack(Material.LAVA_BUCKET),
			new ItemStack(Material.ENDER_PEARL, getRandom(12)), new ItemStack(Material.EXP_BOTTLE, getRandom(24)),
			new ItemStack(Material.DIAMOND, getRandom(3)), new ItemStack(Material.POTION, 1, (short) 16421),
			new ItemStack(Material.BOW), new ItemStack(Material.ARROW, getRandom(64)),
			new ItemStack(Material.DIAMOND_AXE), new ItemStack(Material.DIAMOND_PICKAXE) };

	public MiniFeast(Manager manager, int size) {
		super(manager);

		this.size = size;
		this.chestsData = new ArrayList<>();

		x = getCoord(150);
		z = getCoord(150);

		this.location = Bukkit.getWorlds().get(0).getHighestBlockAt(x, z).getLocation();

		y = Bukkit.getWorlds().get(0).getHighestBlockAt(x, z).getLocation().getBlockY();

		for (FutureBlock futureBlock : manager.getBO2().load(location,
				getManager().getFileManager().getMiniFeastFile())) {
			getBlocks().add(futureBlock);
		}
		
		createStructure();

		Location loc1, loc2, loc3, loc4;

		loc1 = new Location(Bukkit.getWorld("world"), x + 1, y + 1, z + 1);
		loc1.getBlock().setType(Material.CHEST);
		chestsData.add(loc1.getBlock());

		loc2 = new Location(Bukkit.getWorld("world"), x + 1, y + 1, z + -1);
		loc2.getBlock().setType(Material.CHEST);
		chestsData.add(loc2.getBlock());

		loc3 = new Location(Bukkit.getWorld("world"), x - 1, y + 1, z + 1);
		loc3.getBlock().setType(Material.CHEST);
		chestsData.add(loc3.getBlock());

		loc4 = new Location(Bukkit.getWorld("world"), x - 1, y + 1, z + -1);
		loc4.getBlock().setType(Material.CHEST);
		chestsData.add(loc4.getBlock());

		for (Block chests : chestsData) {
			if (chests.getType() == Material.CHEST) {
				chests.setType(Material.CHEST);
				
				Chest chest = (Chest) chests.getLocation().getBlock().getState();

				addChestItems(chest, feastStacks);
				chest.update();
			}
		}

	}

	public Location getLocation() {
		return location;
	}

	public void spawnMiniFeast() {
		forceMiniFeast();
	}

	public void forceMiniFeast() {
		createArea();
		Bukkit.broadcastMessage(
				"§4§lMINIFEAST §7Spawnou aproximadamente em §c(x: " + getInteger(x) + " e z: " + getInteger(z) + ")");
		for (FutureBlock block : getBlocks()) {
			block.place();
		}
		spawnChests();
	}

	public int getInteger(int i) {
		int ad = new Random().nextBoolean() ? -50 : 50;
		return ad + i;
	}

	public void createStructure() {
		createArea();
		for (FutureBlock block : getBlocks()) {
			if (block.getId() != Material.CHEST.getId() && block.getId() != Material.ENCHANTMENT_TABLE.getId()) {
				block.place();
				if (new Random().nextInt(5) + 1 == 5) {
					block.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getId());
				}
			}
		}
	}

	public void spawnChests() {
		new BukkitRunnable() {
			public void run() {
				for (FutureBlock block : getBlocks()) {
					if (block.getId() == Material.CHEST.getId()) {
						block.place();
						addChestItems((Chest) block.getLocation().getBlock().getState(), feastStacks);
					}
					if (block.getId() == Material.ENCHANTMENT_TABLE.getId())
						block.place();
				}
			}
		}.runTaskLater(getManager().getPlugin(), 20L);

		((World) Bukkit.getServer().getWorlds().get(0)).strikeLightningEffect(location.clone().add(0, 1, 0));

	}

	private void createArea() {
		for (int x = -size; x <= size; x++) {
			for (int z = -size; z <= size; z++) {
				if (location.clone().add(x, 0, z).distance(location) > size + 10.D)
					continue;
				for (int y = 0; y <= 20; y++) {
					getManager().getBO2().setBlockFast(
							location.clone().add(x, y, z).getBlock().getLocation(), Material.AIR, (byte) 0);
				}
			}
		}
	}

	private int getRandom(int i) {
		return Math.max(1, new Random().nextInt(i + 1));
	}

	private int getCoord(int range) {
		int cord = new Random().nextInt(range) + 200;
		cord = (new Random().nextBoolean() ? -cord : cord);

		return cord;
	}

	public void addChestItems(final Chest chest, final ItemStack[] stacks) {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (int i = 0; i < stacks.length; i++)
					if (new Random().nextInt(100) < 9)
						chest.getInventory().setItem(new Random().nextInt(27), stacks[i]);
				chest.update();
			}		
		}.runTaskLater(getManager().getPlugin(), 30L);
	}

}
