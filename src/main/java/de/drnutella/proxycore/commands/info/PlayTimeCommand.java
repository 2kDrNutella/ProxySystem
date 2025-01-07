package de.drnutella.proxycore.commands.info;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.TimeCalculator;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import de.drnutella.proxycore.objects.CustomProxyPlayer;

public class PlayTimeCommand extends Command {

    public PlayTimeCommand() {
        super("playtime", null, "pt");
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {

        if(sender instanceof ProxiedPlayer player){
            if(args.length == 0){
                if(player.isConnected()){
                    //lädt die gespeicherten UserInformationen aus dem Cache
                    final CustomProxyPlayer customProxyPlayer = ProxyCore.getCacheManager().userInformationCache.get(player.getUniqueId());

                    final int currentOnlineTime = (int) ((System.currentTimeMillis() - customProxyPlayer.getLoginTimeInMillis()) / 1000);
                    final long savedSeconds = customProxyPlayer.getPlaytimeInSeconds();

                    final String playtimeString = TimeCalculator.fromLongToTimeString(savedSeconds + currentOnlineTime).toTimeString();

                    player.sendMessage(ConfigFileAdapter.PREFIX + "§fDeine Spielzeit beträgt: §b" + playtimeString);
                }

            }else {
                sender.sendMessage(ConfigFileAdapter.PREFIX + "§cBitte benuzte /playtime!");
            }
        }
    }
}
