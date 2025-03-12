package de.drnutella.proxycore.listener;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.data.CacheManager;
import de.drnutella.proxycore.data.implementation.UserBasicInformationService;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import de.drnutella.proxycore.utils.configs.PermissionsFileAdapter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LogoutListener implements Listener {

    final static LuckPerms luckPerms = LuckPermsProvider.get();

    @EventHandler
    public void logut(PlayerDisconnectEvent event){
        final ProxiedPlayer player = event.getPlayer();

        UserBasicInformationService.refreshPlaytime(player, feedback ->{});

        try {
            if (ProxyCore.getPermissionHandler().hasPermission(player, PermissionsFileAdapter.PERMISSION_COMMAND_TEAM)) {
                final User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                final String userRangString = user.getCachedData().getMetaData().getPrefix().replace("&", "§");

                for (final ProxiedPlayer all : ProxyCore.getInstance().getProxy().getPlayers()) {
                    if (ProxyCore.getPermissionHandler().hasPermission(all, PermissionsFileAdapter.PERMISSION_COMMAND_TEAM)) {
                        all.sendMessage(ConfigFileAdapter.PREFIX + userRangString  + " " +  player.getDisplayName() + " §7hat das Netzwerk §cverlassen");
                    }
                }
            }
        }catch (NullPointerException ignored){
            ProxyCore.getInstance().getLogger().warning("Luckperms schein nicht richtig konfiguriert zu sein! Lösungsansatz: ");
            ProxyCore.getInstance().getLogger().warning(" 1. Überprüfe, ob jede Gruppe die in der ProxyCore Config eingetragen ist, vorhanden ist.");
            ProxyCore.getInstance().getLogger().warning(" 2. Überprüfe, ob jede dieser Gruppe in Luckperms einen Prefix hat");
        }

        CacheManager.clearPlayerFromAllCaches(player.getUniqueId());
        CacheManager.storedUUID.remove(player.getName());
    }
}
