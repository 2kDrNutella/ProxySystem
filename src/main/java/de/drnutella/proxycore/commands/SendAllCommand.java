package de.drnutella.proxycore.commands;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import de.drnutella.proxycore.utils.configs.PermissionsFileAdapter;

public class SendAllCommand extends Command {

    final static String PREFIX = ConfigFileAdapter.PREFIX;

    public SendAllCommand() {
        super("send-all", null, "move-all", "sendall", "moveall");
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        if (ProxyCore.getPermissionHandler().hasPermission(commandSender, PermissionsFileAdapter.PERMISSION_SEND_ALL)) {
            if (args.length == 1) {
                final String targetServerName = args[0];

                if (ProxyServer.getInstance().getServerInfo(targetServerName) != null) {
                    final ServerInfo targetServerInfo = ProxyServer.getInstance().getServerInfo(targetServerName);
                    for (final ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                        all.connect(targetServerInfo);
                        all.sendMessage(PREFIX + "§aDu wurdest auf den Server §7[§e" + targetServerName + "§7]§a gesendet!");
                    }
                } else {
                    commandSender.sendMessage(PREFIX + "§cDer Server wurde nicht gefunden!");
                }

            } else {
                commandSender.sendMessage(PREFIX + "§cFalsche Syntax! Bitte benutze /send-all [Server]");
            }
        }
    }
}
