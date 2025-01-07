package de.drnutella.proxycore.listener;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import de.drnutella.proxycore.utils.configs.DynamicVariableFileAdapter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import de.drnutella.proxycore.utils.configs.PermissionsFileAdapter;

public class PostLoginListener implements Listener {

    final static LuckPerms luckPerms = LuckPermsProvider.get();

    @EventHandler
    public void PostLogin(PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        if (DynamicVariableFileAdapter.isWartungActive) {
            if (!ProxyCore.getPermissionHandler().hasPermission(player, PermissionsFileAdapter.PERMISSION_IGNORE_MAINTENANCE)) {
                event.getPlayer().disconnect("§cWartungsarbeiten / Maintenance");
                return;
            }
        }
        callDatabase(event.getPlayer());

        try {
            if (ProxyCore.getPermissionHandler().hasPermission(player, PermissionsFileAdapter.PERMISSION_COMMAND_TEAM)) {
                final User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                final String userRangString = user.getCachedData().getMetaData().getPrefix().replace("&", "§");

                for (final ProxiedPlayer all : ProxyCore.getInstance().getProxy().getPlayers()) {
                    if (ProxyCore.getPermissionHandler().hasPermission(all, PermissionsFileAdapter.PERMISSION_COMMAND_TEAM)) {
                        all.sendMessage(ConfigFileAdapter.PREFIX + userRangString + " " + player.getDisplayName() + " §7hat das Netzwerk §abetreten");
                    }
                }
            }
        } catch (NullPointerException ignored) {
            ProxyCore.getInstance().getLogger().warning("Luckperms schein nicht richtig konfiguriert zu sein! Lösungsansatz: ");
            ProxyCore.getInstance().getLogger().warning(" 1. Überprüfe, ob jede Gruppe die in der ProxyCore Config eingetragen ist, vorhanden ist.");
            ProxyCore.getInstance().getLogger().warning(" 2. Überprüfe, ob jede dieser Gruppe in Luckperms einen Prefix hat");
        }
    }

    static void callDatabase(final ProxiedPlayer caller) {
        ProxyCore.getDatabaseManager().userInformationDataAdapter.refreshPlayerOrCreateIt(caller, callFeedback -> {
            if (!callFeedback) {
                caller.disconnect("§cDatabase Error {1}, please try again");
            }

            ProxyCore.getDatabaseManager().userInformationDataAdapter.createUserObjectFromDatabase(caller, callFeedback2 -> {
                if (!callFeedback2) {
                    caller.disconnect("§cDatabase Error {1}, please try again");
                }
            });
        });
    }
}
