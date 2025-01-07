package de.drnutella.proxycore.handler;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PermissionHandler {

    final static LuckPerms luckPerms = LuckPermsProvider.get();

    public boolean hasPermission(final ProxiedPlayer player, final String permission) {
        final User user = luckPerms.getUserManager().getUser(player.getUniqueId());

        if (user != null) {
            return user.getCachedData().getPermissionData().checkPermission(permission).asBoolean();
        } else {
            return false;
        }
    }

    public boolean hasPermission(final CommandSender commandSender, final String permission) {
        if(commandSender instanceof ProxiedPlayer player){
            return hasPermission(player, permission);
        }else {
            return commandSender.getPermissions().contains(permission);
        }
    }
}
