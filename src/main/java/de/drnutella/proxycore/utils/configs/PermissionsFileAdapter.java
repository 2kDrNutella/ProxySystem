package de.drnutella.proxycore.utils.configs;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.ConfigBuilder;

public class PermissionsFileAdapter {
    final static ConfigBuilder builder = ProxyCore.getPermissionsBuilder();

    public static final String PERMISSION_WARTUNG = (String) builder.getConfig().get("proxy-wartung");
    public static final String PERMISSION_TEAMCHAT = (String) builder.getConfig().get("proxy-teamchat");
    public static final String PERMISSION_IGNORE_MAINTENANCE = (String) builder.getConfig().get("proxy-ignore-wartung");
    public static final String PERMISSION_FIND_PLAYER = (String) builder.getConfig().get("proxy-find-Player");
    public static final String PERMISSION_JUMPTO_PLAYER = (String) builder.getConfig().get("proxy-jumpto-Player");
    public static final String PERMISSION_SEND_ALL = (String) builder.getConfig().get("proxy-send-all");
    public static final String PERMISSION_PLAYER_INFO = (String) builder.getConfig().get("proxy-playerinfo");
    public static final String PERMISSION_COMMAND_TEAM = (String) builder.getConfig().get("proxy-command-team");
}
