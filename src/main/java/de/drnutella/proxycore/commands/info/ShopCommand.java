package de.drnutella.proxycore.commands.info;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ShopCommand extends Command {

    public ShopCommand() {
        super("shop", null, "store", "kaufen", "geldausgeben");
    }

    @Override
    public void execute(final CommandSender commandSender, final String[] args) {
        commandSender.sendMessage(
                """
                        \s
                        §4§lAktuelle bieten wir keinen Shop an!\s
                        §4§l✖✖✖\s
                        \s
                        """
        );
    }
}
