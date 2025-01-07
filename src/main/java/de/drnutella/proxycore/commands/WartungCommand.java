package de.drnutella.proxycore.commands;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import de.drnutella.proxycore.utils.configs.DynamicVariableFileAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import de.drnutella.proxycore.utils.configs.PermissionsFileAdapter;

public class WartungCommand extends Command {

    final static String PREFIX = ConfigFileAdapter.PREFIX;

    public WartungCommand() {
        super("wartung", null, "maintenance");
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        if (ProxyCore.getPermissionHandler().hasPermission(commandSender, PermissionsFileAdapter.PERMISSION_WARTUNG)) {
            if (args.length == 0) {

                if (DynamicVariableFileAdapter.isWartungActive) {
                    commandSender.sendMessage(PREFIX + "§6Der Wartungsmodus ist §aAktiviert§7!");
                } else {
                    commandSender.sendMessage(PREFIX + "§6Der Wartungsmodus ist §cnicht Aktiviert§7!");
                }

            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("an")) {
                    if (!DynamicVariableFileAdapter.isWartungActive) {
                        DynamicVariableFileAdapter.setWartung(true);
                        commandSender.sendMessage(PREFIX + "§6Der Wartungsmodus wurde §aAktiviert§7!");

                        for (final ProxiedPlayer all : ProxyCore.getInstance().getProxy().getPlayers()) {
                            if (!all.hasPermission(PermissionsFileAdapter.PERMISSION_IGNORE_MAINTENANCE)) {
                                all.disconnect("§cDer Wartungsmodus wurde aktiviert! \n§cBitte versuche es später erneut!");
                            }
                        }

                    } else {
                        commandSender.sendMessage(PREFIX + "§6Der Wartungsmodus ist bereits §aAktiviert§7!");
                    }
                } else if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("aus")) {
                    if (DynamicVariableFileAdapter.isWartungActive) {
                        DynamicVariableFileAdapter.setWartung(false);
                        commandSender.sendMessage(PREFIX + "§6Der Wartungsmodus wurde §cdeaktivert§7!");
                    } else {
                        commandSender.sendMessage(PREFIX + "§6Der Wartungsmodus ist bereits §cdeaktivert§7!");
                    }
                }
            }
        }
    }
}
