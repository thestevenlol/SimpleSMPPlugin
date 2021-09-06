package me.stevenlol.simplesmp.commands.moderation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.events.BanEvent;
import me.stevenlol.simplesmp.utilities.Color;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class BanCommand extends BaseCommand {

    @CommandAlias("ban")
    @CommandPermission("ssmp.ban")
    public void ban(Player player, String[] args) {
        // ban player reason

        switch (args.length) {
            case 0:
                player.sendMessage(Color.translate(Main.getPrefix() + "&c/ban <player> <reason>"));
                break;
            case 1:
                player.sendMessage(Color.translate(Main.getPrefix() + "&c/ban " + args[0] + " <reason>"));
                break;
            default:
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

                // todo actually ban the cunt

                Main.getPlugin().getServer().getPluginManager().callEvent(new BanEvent(reason, target, player));
        }

    }

}
