package de.drnutella.proxycore.commands.info;

import de.drnutella.castigo.data.api.implementation.PunishService;
import de.drnutella.castigo.objects.PunishInfo;
import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.objects.CustomProxyPlayer;
import de.drnutella.proxycore.utils.TimeCalculator;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PlayerInfoCommand extends Command {

    public PlayerInfoCommand() {
        super("playerinfo", null, "pi", "playeri");
    }

    // /playerinfo [name]

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        if (args.length == 1) {

            new CustomProxyPlayer(args[0], customProxyPlayer -> {
                if (customProxyPlayer != null) {

                    commandSender.sendMessage("§7--- §b" + args[0] + "§7---");
                    commandSender.sendMessage("§f ➝ §bLastLogin§7: §f" + customProxyPlayer.loginTimestamp());

                    if(ProxyCore.getInstance().getProxy().getPlayer(customProxyPlayer.uuid()) != null) {
                        commandSender.sendMessage("§f ➝ §bOnline Seit: §f" + customProxyPlayer.onlineTimeString());
                    }

                    commandSender.sendMessage("§f ➝ §bUUID: §f" + customProxyPlayer.uuid());
                    PunishService.loadPunishInfoContainer(customProxyPlayer.uuid(), punishInfoContainer -> {
                        sendMute(punishInfoContainer.lastChatPunish(), commandSender);
                        sendBan(punishInfoContainer.lastNetworkPunish(), commandSender);
                    });
                    commandSender.sendMessage("§f ➝ §bPlaytime: §f" + customProxyPlayer.playTimeAsString());
                    return;
                }
                commandSender.sendMessage("§cEs wurden keine Daten gefunden!");
            });
        }
    }

    void sendMute(PunishInfo punishInfo, CommandSender commandSender) {
        if (punishInfo != null) {
            if (punishInfo.isActive()) {
                commandSender.sendMessage("§f ➝ §bMute: §e" + TimeCalculator.convertMillisToReadableTime(punishInfo.remainingTimeMillis()));
                commandSender.sendMessage("§f ➥ §bGrund: §e" + punishInfo.reason());
                return;
            }
        }
        commandSender.sendMessage("§f ➝ §bMute: " + "§4✖");

    }

    void sendBan(PunishInfo punishInfo, CommandSender commandSender) {
        if (punishInfo != null) {
            if (punishInfo.isActive()) {
                commandSender.sendMessage("§f ➝ §bBan: §e" + TimeCalculator.convertMillisToReadableTime(punishInfo.remainingTimeMillis()));
                commandSender.sendMessage("§f ➥ §bGrund: §e" + punishInfo.reason());
                return;
            }
        }
        commandSender.sendMessage("§f ➝ §bBan: " + "§4✖");
    }
}
