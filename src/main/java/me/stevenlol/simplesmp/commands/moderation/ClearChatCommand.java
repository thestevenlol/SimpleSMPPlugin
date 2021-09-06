package me.stevenlol.simplesmp.commands.moderation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClearChatCommand extends BaseCommand {

    @CommandAlias("cc|clearchat")
    @CommandPermission("ssmp.clearchat")
    public void cc(Player player) {
        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage(" ");
        }
        Bukkit.broadcastMessage(Color.translate(Main.getPrefix() + "&cChat has been cleared by an administrator"));
    }

}
