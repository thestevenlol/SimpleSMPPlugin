package me.stevenlol.simplesmp.commands.homes;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HomeCommand extends BaseCommand {

    @CommandAlias("home")
    @CommandPermission("ssmp.home")
    public void home(Player player, String[] args) {
        if (args.length == 0) {
            SQLUtils.getHomeLocation(player, "home", location -> {
                if (location == null) {
                    player.sendMessage(Color.translate(Main.getPrefix() + "&cYou do not have a home set!"));
                    return;
                }
                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> player.teleport(location));
            });
        } else if (args.length == 1) {
            SQLUtils.getHomeLocation(player, args[0], location -> {
                if (location == null) {
                    player.sendMessage(Color.translate(Main.getPrefix() + "&cYou do not have a home named " + args[0] + " set!"));
                    return;
                }
                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> player.teleport(location));
            });
        }
    }

}
