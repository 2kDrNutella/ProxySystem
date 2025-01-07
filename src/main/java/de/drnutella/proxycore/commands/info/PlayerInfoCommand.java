package de.drnutella.proxycore.commands.info;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.TimeCalculator;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import de.drnutella.proxycore.objects.CustomProxyPlayer;
import de.drnutella.proxycore.objects.UserInfoObject;
import de.drnutella.proxycore.utils.configs.PermissionsFileAdapter;

public class PlayerInfoCommand extends Command {

    public PlayerInfoCommand() {
        super("playerinfo", null, "pi", "playeri");
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (ProxyCore.getPermissionHandler().hasPermission(sender, PermissionsFileAdapter.PERMISSION_PLAYER_INFO)) {
            if (args.length == 1) {
                try {
                    ProxyCore.getDatabaseManager().userInformationDataAdapter.getUUIDByUsername(args[0], uuidfeedback -> {
                        if (uuidfeedback != null) {
                            ProxyCore.getDatabaseManager().userInformationDataAdapter.getUserInfoFromDatabaseByUUID(uuidfeedback, args[0], userInfoObject -> {
                                sender.sendMessage("§7--- §b" + userInfoObject.getUsername() + " §7--- \n");

                                if (ProxyCore.getCacheManager().userInformationCache.containsKey(uuidfeedback)) {
                                    final CustomProxyPlayer customProxyPlayer = ProxyCore.getCacheManager().userInformationCache.get(uuidfeedback);
                                    sender.sendMessage(
                                            "§bLastLogin§7: §f" + userInfoObject.getLastLogin() +
                                                    " §7(§a§lOnline §7- §b" + customProxyPlayer.getPlayer().getServer().getInfo().getName() +
                                                    "§7)");

                                    sendBasicUserInfos(sender, userInfoObject);
                                    sender.sendMessage("§bOnline seit§7: §f" +
                                            TimeCalculator.fromLongToTimeString(customProxyPlayer.getCurrentPlaytime()).toTimeString());

                                } else {
                                    sender.sendMessage("§bLastLogin§7: §f" + userInfoObject.getLastLogin() + " §7(§c§lOffline§7)");
                                    sendBasicUserInfos(sender, userInfoObject);
                                }

                                sender.sendMessage("§bPlaytime§7: §f" +
                                        TimeCalculator.fromLongToTimeString(userInfoObject.getPlaytimeInSeconds()).toTimeString() + "\n");
                            });
                        } else {
                            sender.sendMessage(ConfigFileAdapter.PREFIX + "§cDer User wurde nicht gefunden!");
                        }
                    });

                } catch (NullPointerException ignored) {
                    sender.sendMessage(ConfigFileAdapter.PREFIX + "§cDatenbank Fehler, bitte versuche es erneut!");
                }
            } else {
                sender.sendMessage(ConfigFileAdapter.PREFIX + "§cFalsche Syntax! Bitte benutzte §b/playerinfo §7[§eName§7]");
            }
        }
    }

    static void sendBasicUserInfos(CommandSender reciver, UserInfoObject userInfoObject) {
        reciver.sendMessage("§bUUID§7: §f" + userInfoObject.getUuid());
        reciver.sendMessage("§bUsername§7: §f" + userInfoObject.getUsername());
        reciver.sendMessage("§bCoins§7: §f" + userInfoObject.getCoins());
        reciver.sendMessage("§bJuweelen§7: §f" + userInfoObject.getJuweelen());
    }
}
