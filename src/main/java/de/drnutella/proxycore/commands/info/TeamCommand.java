package de.drnutella.proxycore.commands.info;

import de.drnutella.proxycore.ProxyCore;
import de.drnutella.proxycore.utils.StringUtils;
import de.drnutella.proxycore.utils.configs.ConfigFileAdapter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import de.drnutella.proxycore.utils.configs.PermissionsFileAdapter;

import java.util.List;

public class TeamCommand extends Command {

    private static final LuckPerms luckPerms = LuckPermsProvider.get();
    private final List<String> groupsToDisplay; // Liste der Gruppen, die angezeigt werden sollen

    public TeamCommand() {
        super("team", null, "teamlist");
        this.groupsToDisplay = List.of(ConfigFileAdapter.TEAM_RANKS.split(" "));
    }

    @Override
    public void execute(final CommandSender sender, final String[] strings) {
        if(ProxyCore.getPermissionHandler().hasPermission(sender, PermissionsFileAdapter.PERMISSION_COMMAND_TEAM)) {
            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage(new TextComponent("Dieser Befehl kann nur von einem Spieler ausgeführt werden."));
                return;
            }
            sender.sendMessage("\n §7----- §a§lAktuell Online: §7----- \n");


            for (final String groupName : groupsToDisplay) {
                final Group group = luckPerms.getGroupManager().getGroup(groupName.trim());
                if (group == null) {
                    continue;
                }

                final List<ProxiedPlayer> groupMembers = ProxyCore.getInstance().getProxy().getPlayers().stream()
                        .filter(targetPlayer -> {
                            User user = luckPerms.getUserManager().getUser(targetPlayer.getUniqueId());
                            if (user == null) return false;

                            return user.getCachedData().getMetaData().getPrimaryGroup().equalsIgnoreCase(groupName);
                        })
                        .toList();

                if (!groupMembers.isEmpty()) {
                    final String colorCode = StringUtils.getColorCodeFromGroup(group);
                    final String coloredGroupName = colorCode + group.getName();

                    sender.sendMessage(new TextComponent(coloredGroupName + "§f:"));
                    for (final ProxiedPlayer member : groupMembers) {
                        final User user = luckPerms.getUserManager().getUser(member.getUniqueId());
                        if (user != null) {
                            final TextComponent playerNameComponent = new TextComponent(" §f➥ " + colorCode + member.getName());
                            final TextComponent playerServerComponent = serverTextComponent(member);
                            sender.sendMessage(playerNameComponent, playerServerComponent);
                        }
                    }
                    sender.sendMessage("");
                }
            }
        }
    }

    private static TextComponent serverTextComponent(final ProxiedPlayer member) {
        final TextComponent playerServerComponent = new TextComponent(" §7(§e" + member.getServer().getInfo().getName() + "§7)");
        playerServerComponent.setHoverEvent(
                new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new Text("Klicke, um auf den Server von " + member.getName() + " zu joinen")
                )
        );
        playerServerComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/jumpto " + member.getName()));
        return playerServerComponent;
    }

}
