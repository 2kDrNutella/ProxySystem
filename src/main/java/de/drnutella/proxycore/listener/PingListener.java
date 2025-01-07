package de.drnutella.proxycore.listener;

import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import de.drnutella.proxycore.utils.configs.DynamicVariableFileAdapter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PingListener implements Listener {

    final static String normalLine1 = ChatColor.translateAlternateColorCodes('&', ConfigFileAdapter.MOTD_NORMAL_LINE_1);
    final static String normalLine2 = ChatColor.translateAlternateColorCodes('&', ConfigFileAdapter.MOTD_NORMAL_LINE_2);

    final static String wartungMotdLine1 = ChatColor.translateAlternateColorCodes('&', ConfigFileAdapter.MOTD_WARTUNG_LINE_1);
    final static String wartungMotdLine2 = ChatColor.translateAlternateColorCodes('&', ConfigFileAdapter.MOTD_WARTUNG_LINE_2);

    @EventHandler
    public void ProxyPing(ProxyPingEvent event){
        final ServerPing connection = event.getResponse();

        if(DynamicVariableFileAdapter.isWartungActive){
            connection.setVersion(new ServerPing.Protocol("§c§lWartungsarbeiten", 1));
            connection.setDescription(wartungMotdLine1 + "\n" + wartungMotdLine2);
        }else {
            connection.setDescription(normalLine1 + "\n" + normalLine2);
        }

        event.setResponse(connection);
    }
}
