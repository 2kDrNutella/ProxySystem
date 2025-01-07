package de.drnutella.proxycore.utils.configs;

import net.md_5.bungee.api.ChatColor;
import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.ConfigBuilder;

public class ConfigFileAdapter {
    final static ConfigBuilder builder = ProxyCore.getConfigBuilder();

    public final static String PREFIX =
            ChatColor.translateAlternateColorCodes('&', (String) builder.getConfig().get("prefix"));

    public static String MOTD_NORMAL_LINE_1 = (String) builder.getConfig().get("motd-normal-line1");
    public static String MOTD_NORMAL_LINE_2 = (String) builder.getConfig().get("motd-normal-line2");
    public static String MOTD_WARTUNG_LINE_1 = (String) builder.getConfig().get("motd-wartung-line1");
    public static String MOTD_WARTUNG_LINE_2 = (String) builder.getConfig().get("motd-wartung-line2");
    public static String TEAM_RANKS = (String) builder.getConfig().get("teamRanks");

}
