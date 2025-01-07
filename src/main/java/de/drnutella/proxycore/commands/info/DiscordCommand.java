package de.drnutella.proxycore.commands.info;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class DiscordCommand extends Command {

    public DiscordCommand() {
        super("Discord", null, "dc", "dcord");
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        commandSender.sendMessage(
                """
                \s
                Unsern Discord findest du hier:\s
                https://discord.to/drnutellaistcool.com\s
                \s
                """
        );

    }
}
