package de.drnutella.proxycore.data;

import de.drnutella.proxycore.objects.CustomProxyPlayer;

import java.util.HashMap;
import java.util.UUID;

/*
    Globaler Speicherort für Caches
 */

public class CacheManager {
    public HashMap<UUID, CustomProxyPlayer> userInformationCache = new HashMap<>();
}
