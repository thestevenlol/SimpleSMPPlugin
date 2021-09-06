package me.stevenlol.simplesmp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.scoreboards.ScoreboardMain;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChannelCommand extends BaseCommand {

    @CommandAlias("staff|st")
    @CommandPermission("ssmp.staff")
    public void staff(Player player) {
        SQLUtils.getChannel(player, channel -> {
            if (channel.equals("global")) {
                SQLUtils.setChannel(player, "staff");
                player.sendMessage(Color.translate(Main.getPrefix() + "&aYou are now in Staff Chat."));
              return;
            }
            SQLUtils.setChannel(player, "global");
            player.sendMessage(Color.translate(Main.getPrefix() + "&aYou are no longer in Staff Chat."));
        });
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> ScoreboardMain.updateScoreboard(player), 20);
    }

}
