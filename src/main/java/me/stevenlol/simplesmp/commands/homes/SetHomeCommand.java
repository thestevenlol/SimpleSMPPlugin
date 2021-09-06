package me.stevenlol.simplesmp.commands.homes;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import org.bukkit.entity.Player;

public class SetHomeCommand extends BaseCommand {

    @CommandAlias("sethome|sh")
    @CommandPermission("ssmp.sethome")
    public void sethome(Player player, String[] args) {
        if (args.length == 0) {
            SQLUtils.setHomeLocation(player, "home");
            player.sendMessage(Color.translate(Main.getPrefix() + "&aHome location set."));
        } else if (args.length == 1) {
            SQLUtils.setHomeLocation(player, args[0]);
            player.sendMessage(Color.translate(Main.getPrefix() + "&aHome location set."));
        } else {
            player.sendMessage(Color.translate(Main.getPrefix() + "&cHome names can only be one word long."));
        }
    }

}
