package me.stevenlol.simplesmp.commands.moderation;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandAlias("note")
public class NotesCommand extends BaseCommand {

    @Subcommand("add")
    @CommandPermission("ssmp.notes.add")
    @CommandCompletion("@OfflinePlayers @nothing")
    public void addNote(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(Color.translate(Main.getPrefix() + "&c/note add <player> <note>"));
        } else if (args.length == 1) {
            player.sendMessage(Color.translate(Main.getPrefix() + "&c/note add " + args[0] + " <note>"));
        } else {

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (!target.hasPlayedBefore()) {
                player.sendMessage(Color.translate(Main.getPrefix() + "&c" + args[0] + " has never played on the server before."));
                return;
            }

            String note;
            StringBuilder x = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                x.append(args[i]).append(" ");
            }
            note = x.toString().trim();

            SQLUtils.setNote(target, note);
            player.sendMessage(Color.translate(Main.getPrefix() + "&eNote has been made for " + target.getName() + "."));

        }
    }

}
