package me.stevenlol.simplesmp.commands.gamemode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GMS extends BaseCommand {

    @CommandAlias("gms")
    @CommandPermission("ssmp.gms")
    @CommandCompletion("@players")
    public void gmc(Player player, String[] args) {
        if (args.length == 0) {
            if (player.getGameMode().equals(GameMode.SURVIVAL)) {
                player.sendMessage(Color.translate(Main.getPrefix() + "&cYou are already in Survival Mode!"));
                return;
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(Color.translate(Main.getPrefix() + "&aYou are now in Survival Mode!"));
        } else {
            if (player.hasPermission("ssmp.gms.other")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == player) {
                    player.sendMessage(Color.translate(Main.getPrefix() + "&cYou cannot target yourself."));
                    return;
                }
                if (target == null) {
                    player.sendMessage(Color.translate(Main.getPrefix() + "&c" + args[0] + " is not online."));
                    return;
                }
                if (target.getGameMode().equals(GameMode.SURVIVAL)) {
                    player.sendMessage(Color.translate(Main.getPrefix() + "&c" + player.getDisplayName() + " is already in Survival Mode!"));
                    return;
                }
                target.setGameMode(GameMode.SURVIVAL);
                target.sendMessage(Color.translate(Main.getPrefix() + "&aYou are now in Survival Mode!"));
                player.sendMessage(Color.translate(Main.getPrefix() + "&a" + target.getName() + " is now in Survival Mode!"));
            } else {
                player.sendMessage(Color.translate(Main.getPrefix() + "&cYou do not have permission to use this command."));
            }
        }
    }
    
}
