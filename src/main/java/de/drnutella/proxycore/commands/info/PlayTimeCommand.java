package de.drnutella.proxycore.commands.info;

import de.drnutella.proxycore.objects.CustomProxyPlayer;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PlayTimeCommand extends Command {

    public PlayTimeCommand() {
        super("playtime", null, "pt", "ptime");
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        if(commandSender instanceof ProxiedPlayer proxiedPlayer){
            new CustomProxyPlayer(proxiedPlayer.getUniqueId(), customProxyPlayer -> {
                proxiedPlayer.sendMessage(ConfigFileAdapter.PREFIX + "§bDeine Spielzeit beträgt§7: §f" + customProxyPlayer.playTimeAsString());
            });
        }
    }

}
