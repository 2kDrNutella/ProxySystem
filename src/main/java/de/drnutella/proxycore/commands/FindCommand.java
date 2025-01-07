package de.drnutella.proxycore.commands;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import de.drnutella.proxycore.utils.configs.PermissionsFileAdapter;

public class FindCommand extends Command {

    public FindCommand() {
        super("find", null, "whereis", "wi", "finde");
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        if (ProxyCore.getPermissionHandler().hasPermission(commandSender, PermissionsFileAdapter.PERMISSION_FIND_PLAYER)) {
            if (args.length == 1) {
                try {
                    final ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                    final ServerInfo targetServerInfo = target.getServer().getInfo();
                    commandSender.sendMessage(ConfigFileAdapter.PREFIX + "§aDer Spieler befindet sich auf §7[§e" + targetServerInfo.getName() + "§7]");

                } catch (NullPointerException ignored) {
                    commandSender.sendMessage(ConfigFileAdapter.PREFIX + "§cDer Spieler wurde nicht gefunden");
                }
            } else {
                commandSender.sendMessage(ConfigFileAdapter.PREFIX + "§cFalsche Syntax! Bitte benuzte /find [Spielername]");
            }
        }
    }
}
