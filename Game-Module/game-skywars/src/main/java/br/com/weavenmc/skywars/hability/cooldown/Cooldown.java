package br.com.weavenmc.skywars.hability.cooldown;

import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
public class Cooldown {

    @Getter
    @NonNull
    private String name;

    @Getter
    @NonNull
    private Long duration;
    private long startTime = System.currentTimeMillis();

    public double getPercentage() {
        return (getRemaining() * 100) / duration;
    }

    public double getRemaining() {
        long endTime = startTime + TimeUnit.SECONDS.toMillis(duration);
        return (-(System.currentTimeMillis() - endTime)) / 1000D;
    }

    public boolean expired() {
        return getRemaining() < 0D;
    }
}
