package me.stevenlol.simplesmp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.Files;
import org.bukkit.entity.Player;

@CommandAlias("ssmp")
@CommandPermission("ssmp.admin")
public class SSMP extends BaseCommand {

    @Subcommand("sqlconnect")
    public void sqlConnect(Player player) {
        if (Main.getSql().isConnected()) {
            player.sendMessage(Color.translate(Main.getPrefix() + "&aThe database is already connected."));
            return;
        }
        try {
            Main.getSql().connect();
            player.sendMessage(Color.translate(Main.getPrefix() + "&aReconnected to the database!"));
        } catch (Exception e) {
            player.sendMessage(Color.translate(Main.getPrefix() + "&cThere was an issue when trying to connect to the database."));
        }
    }

    @Subcommand("reload")
    public void reload(Player player) {
        Files.reloadFiles();
        player.sendMessage(Color.translate(Main.getPrefix() + "&aAll plugin files have been reloaded."));
    }

}
