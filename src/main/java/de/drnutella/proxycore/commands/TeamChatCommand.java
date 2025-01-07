package de.drnutella.proxycore.commands;

import de.drnutella.proxycore.ProxyCore;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import de.drnutella.proxycore.utils.configs.PermissionsFileAdapter;

public class TeamChatCommand extends Command {

    final static String TEAM_CHAT_PREFIX = "§7[§b§lTeamChat§7]§e ";

    public TeamChatCommand() {
        super("teamchat", null, "tc", "teamc", "team");
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        final ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;

        if (ProxyCore.getPermissionHandler().hasPermission(commandSender, PermissionsFileAdapter.PERMISSION_TEAMCHAT)) {
            if (args.length > 0) {
                for (final ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                    if (all.hasPermission(PermissionsFileAdapter.PERMISSION_TEAMCHAT)) {
                        final StringBuilder message = new StringBuilder(args[0]);

                        for (int i = 1; i < args.length; i++) {
                            message.append(" ").append(args[i]);
                        }

                        if (!message.isEmpty()) {
                            all.sendMessage(TEAM_CHAT_PREFIX + proxiedPlayer.getDisplayName() + " §8>>§7 " + message);
                        } else {
                            commandSender.sendMessage(TEAM_CHAT_PREFIX + "§cbitte gebe eine Nachricht an!");
                        }
                    }
                }

            }
        }
    }
}
