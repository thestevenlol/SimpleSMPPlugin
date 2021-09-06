package me.stevenlol.simplesmp.commands.moderation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.events.MuteEvent;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class MuteCommand extends BaseCommand {

    @CommandAlias("mute")
    @CommandPermission("ssmp.mute")
    public void mute(Player player, String[] args) {
        // mute player reason
        if (args.length == 0) {
            player.sendMessage(Color.translate(Main.getPrefix() + "&c/mute <player> <reason>"));
        } else if (args.length == 1) {
            player.sendMessage(Color.translate(Main.getPrefix() + "&c/mute " + args[0] + " <reason>"));
        } else {
            StringBuilder x = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                x.append(args[i]).append(" ");
            }
            String reason = x.toString();

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (!target.hasPlayedBefore()) {
                player.sendMessage(Color.translate(Main.getPrefix() + "&cThat player has never played on the server before!"));
                return;
            }

            SQLUtils.isMuted(target, muted -> {
                if (muted) {
                    player.sendMessage(Color.translate(Main.getPrefix() + "&cThe player " + target.getName() + " is already muted."));
                    return;
                }
                Bukkit.getScheduler().runTask(Main.getPlugin(), () -> Main.getPlugin().getServer().getPluginManager().callEvent(new MuteEvent(reason, target, player)));
            });

        }

    }

}
