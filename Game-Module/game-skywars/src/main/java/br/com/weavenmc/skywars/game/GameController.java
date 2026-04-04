package br.com.weavenmc.skywars.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.block.Block;

import br.com.weavenmc.skywars.player.PlayerManager;

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

public class GameController {
	
	public static ArrayList<UUID> player = new ArrayList<>();
	public static ArrayList<UUID> spectador = new ArrayList<>();
	public static ArrayList<Block> jailBlock = new ArrayList<>();
	
	public static HashMap<UUID, PlayerManager> check = new HashMap<>();
	

}
