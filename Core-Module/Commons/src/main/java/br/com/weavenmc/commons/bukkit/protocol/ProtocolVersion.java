package br.com.weavenmc.commons.bukkit.protocol;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.util.stream.Stream;

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

@RequiredArgsConstructor
public enum ProtocolVersion {

    MINECRAFT_1_11_1(316), 
    MINECRAFT_1_11(315), 
    MINECRAFT_1_10(210), 
    MINECRAFT_1_9_4(110), 
    MINECRAFT_1_9_2(109), 
    MINECRAFT_1_9_1(108), 
    MINECRAFT_1_9(107), 
    MINECRAFT_1_8(47), 
    MINECRAFT_1_7_10(5), 
    MINECRAFT_1_7_5(4), 
    UNKNOWN(-1); 

    @Getter
    @NonNull
    private Integer id;

    public static ProtocolVersion getById(int id) {
        return Stream.of(values()).filter(e -> e.getId() == id).findFirst().orElse(UNKNOWN);
    }
}
