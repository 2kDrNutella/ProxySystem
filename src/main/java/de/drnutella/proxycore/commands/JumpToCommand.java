package de.drnutella.proxycore.commands;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import de.drnutella.proxycore.utils.configs.PermissionsFileAdapter;

public class JumpToCommand extends Command {

    final static String PREFIX = ConfigFileAdapter.PREFIX;

    public JumpToCommand() {
        super("jumpto", null, "jt", "goto", "gt");
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        if(ProxyCore.getPermissionHandler().hasPermission(commandSender, PermissionsFileAdapter.PERMISSION_JUMPTO_PLAYER)) {
            if (commandSender instanceof ProxiedPlayer player) {
                if (args.length == 1) {
                    try {
                        final ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[0]);
                        final ServerInfo targerServerInfo = target.getServer().getInfo();

                        if (!player.equals(target)) {
                            if (!player.getServer().getInfo().equals(target.getServer().getInfo())) {
                                player.connect(targerServerInfo);
                                player.sendMessage(PREFIX + "§aDu wirst auf §7[§e" + targerServerInfo.getName() + "§7] §agesendet!");
                            } else {
                                player.sendMessage(PREFIX + "§cDu bist bereits auf diesem Server!");
                            }
                        } else {
                            player.sendMessage(PREFIX + "§cDu kannst nicht zu dir selber springen!");
                        }
                    } catch (NullPointerException ignored) {
                        player.sendMessage(PREFIX + "§cDieser Spieler wurde nicht gefunden!");
                    }
                } else {
                    commandSender.sendMessage(PREFIX + "§cFalsche Syntax! Bitte benutzte /jumpto [Spielername]");
                }

            } else {
                commandSender.sendMessage(PREFIX + "§cDu musst ein Spieler sein!");
            }
        }
    }
}
