package de.drnutella.proxycore.data.implementation;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.data.CacheManager;
import de.drnutella.proxycore.data.dataAdapter.UserBasicInformationDataAdapter;
import de.drnutella.proxycore.objects.UserInformation;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class UserBasicInformationService extends UserBasicInformationDataAdapter {

    public static void refreshPlayerOrCreateIt(final ProxiedPlayer proxiedPlayer, Consumer<Boolean> callback) {
        refreshPlayerOrCreateItSQL(proxiedPlayer, callback::accept);
    }

    public static void refreshPlaytime(final ProxiedPlayer proxiedPlayer, Consumer<Boolean> callback) {
        refreshPlaytimeSQL(proxiedPlayer, callback::accept);
    }

    public static void getUUIDFromUserName(String username, Consumer<UUID> callback) {
        if (CacheManager.storedUUID.containsKey(username)) {
            callback.accept(CacheManager.storedUUID.get(username));
            return;
        }
        getUUIDByUsernameSQL(username, callback::accept);
    }

    public static void loadPlayerDatabaseInformation(UUID uuid, Consumer<UserInformation> callback) {
        if (!CacheManager.storedUserInformation.containsKey(uuid)) {
            getUserInfoFromDatabaseByUUIDSQL(uuid, information -> {
                if (ProxyCore.getInstance().getProxy().getPlayer(uuid) != null) {
                    CacheManager.storedUserInformation.put(uuid, information);
                }
                callback.accept(information);
            });
        } else {
            callback.accept(CacheManager.storedUserInformation.get(uuid));
        }
    }
}
