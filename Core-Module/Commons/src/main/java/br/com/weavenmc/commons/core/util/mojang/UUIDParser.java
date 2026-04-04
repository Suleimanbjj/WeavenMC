package br.com.weavenmc.commons.core.util.mojang;

import com.google.gson.JsonElement;
import java.util.UUID;

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

public class UUIDParser {

    public static UUID parse(JsonElement element) {
        return parse(element.getAsString());
    }

    public static UUID parse(String string) {
        if (string != null && !string.isEmpty()) {
            if (string.matches("[0-9a-fA-F]{32}")) {
                return UUID.fromString(string.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
            } else if (string.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}")) {
                return UUID.fromString(string);
            }
        }
        return null;
    }
}
