package br.com.weavenmc.commons.bukkit.api.bossbar.entity;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import br.com.weavenmc.commons.bukkit.api.bossbar.BossBarEntity;

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

public class NopeBoss extends BossBarEntity {

    public NopeBoss(Player player) {
        super(player);
    }

    @Override
    public void spawn() {
       
    }

    @Override
    public void remove() {
        
    }

    @Override
    public void update() {
      
    }

    @Override
    public void move(PlayerMoveEvent event) {
       
    }

    @Override
    public boolean setHealth(float percent) {
        return false;
    }

    @Override
    public boolean setTitle(String title) {
        return false;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void setAlive(boolean alive) {
        
    }
}
