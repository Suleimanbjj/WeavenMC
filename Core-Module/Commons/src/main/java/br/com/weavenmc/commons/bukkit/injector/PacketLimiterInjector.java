package br.com.weavenmc.commons.bukkit.injector;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import br.com.weavenmc.commons.bukkit.BukkitMain;

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

public class PacketLimiterInjector {

	private final Map<Player, PPS> ppsMap = new HashMap<>();

    public void inject(BukkitMain main) {
        ProtocolLibrary.getProtocolManager().getAsynchronousManager().registerAsyncHandler(new PacketAdapter(main, ListenerPriority.NORMAL, PacketType.Play.Client.FLYING, PacketType.Play.Client.POSITION) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();
                if (player == null)
                    return;
                PPS pps = ppsMap.computeIfAbsent(player, v -> new PPS());
                if (pps.last > System.currentTimeMillis()) {
                    if (++pps.count > 500) {
                        event.setCancelled(true);
                        if (!pps.kicked) {
                            try {
                                pps.kicked = true;
                                PacketContainer packet = new PacketContainer(PacketType.Play.Server.KICK_DISCONNECT);
                                packet.getChatComponents().write(0, WrappedChatComponent.fromText("§4§lSPEED§f Você está se movendo §c§lMUITO RAPIDO!"));
                                main.getProcotolManager().sendServerPacket(player, packet);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    pps.last = System.currentTimeMillis() + 1000L;
                    pps.count = 0;
                }
            }
        }).start();

        main.getServer().getPluginManager().registerEvents(new Listener() {

            @EventHandler(priority = EventPriority.MONITOR)
            public void onPlayerQuit(PlayerQuitEvent event) {
                ppsMap.remove(event.getPlayer());
            }

        }, main);
    }

    protected class PPS {

        private boolean kicked;
        private long last;
        private int count;

    }
}
