package br.com.weavenmc.commons.bukkit.api.bossbar.entity;

import java.util.Objects;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
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

public class GenericBoss extends BossBarEntity {

    private Object bossBar;

    public GenericBoss(Player player) {
        super(player);
        spawn();
    }

    @Override
    public boolean isAlive() {
        return bossBar != null;
    }

    @Override
    public void spawn() {
        if (!isAlive()) {
            try {
                Class<?> bossColorClass = Class.forName("us.myles.ViaVersion.api.boss.BossColor");
                Class<?> bossStyleClass = Class.forName("us.myles.ViaVersion.api.boss.BossStyle");
                Class<?> viaBossBarClass = Class.forName("us.myles.ViaVersion.boss.ViaBossBar");

                @SuppressWarnings("unchecked")
                Object pink = Enum.valueOf((Class<Enum>) bossColorClass, "PINK");
                @SuppressWarnings("unchecked")
                Object solid = Enum.valueOf((Class<Enum>) bossStyleClass, "SOLID");

                Constructor<?> constructor = viaBossBarClass.getConstructor(String.class, float.class, bossColorClass,
                        bossStyleClass);
                bossBar = constructor.newInstance("", 1F, pink, solid);

                Method addPlayer = viaBossBarClass.getMethod("addPlayer", Player.class);
                addPlayer.invoke(bossBar, getPlayer());
            } catch (ReflectiveOperationException ignored) {
                bossBar = null;
            }
        }
    }

    @Override
    public void remove() {
        if (isAlive()) {
            try {
                Method removePlayer = bossBar.getClass().getMethod("removePlayer", Player.class);
                removePlayer.invoke(bossBar, getPlayer());
            } catch (ReflectiveOperationException ignored) {
            }
            bossBar = null;
        }
    }

    @Override
    public boolean setTitle(String title) {
        if (isAlive()) {
            if (!Objects.equals(this.title, title)) {
                try {
                    Method setTitle = bossBar.getClass().getMethod("setTitle", String.class);
                    setTitle.invoke(bossBar, title);
                } catch (ReflectiveOperationException ignored) {
                }
                this.title = title;
            }
        }
        return false;
    }

    @Override
    public boolean setHealth(float percent) {
        if (isAlive()) {
            float health = percent / 100F;
            if (!Objects.equals(this.health, health)) {
                try {
                    Method setHealth = bossBar.getClass().getMethod("setHealth", float.class);
                    setHealth.invoke(bossBar, health);
                } catch (ReflectiveOperationException ignored) {
                }
                this.health = health;
            }
        }
        return false;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void move(PlayerMoveEvent event) {
        
    }
}
