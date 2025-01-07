package de.drnutella.proxycore.commands;

import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class HubCommand extends Command {

    public HubCommand() {
        super("hub", "", "l", "lobby");
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {

        if (commandSender instanceof ProxiedPlayer player) {
            final ServerInfo lobbyInfo = ProxyServer.getInstance().getServerInfo("Lobby-Server-01");

            if (!(player.getServer().getInfo() == lobbyInfo)) {
                player.connect(lobbyInfo);
                player.sendMessage(ConfigFileAdapter.PREFIX + "§aDu wirst zum Hub geschickt!");
            } else {
                player.sendMessage(ConfigFileAdapter.PREFIX + "§cDu bist bereits am Hub!");
            }

        } else {
            commandSender.sendMessage(ConfigFileAdapter.PREFIX + "§cDu musst ein Spieler sein!");
        }
    }
}
