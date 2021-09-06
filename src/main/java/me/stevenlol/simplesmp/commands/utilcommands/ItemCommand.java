package me.stevenlol.simplesmp.commands.utilcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand extends BaseCommand {

    @CommandAlias("i|item")
    @CommandPermission("ssmp.item")
    public void item(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(Color.translate(Main.getPrefix() + "&c/item <item_name>"));
        } else if (args.length == 1) {
            ItemStack item = new ItemStack(Material.valueOf(args[0].toUpperCase()));
            if (player.getInventory().getSize() == -1) {
                player.sendMessage(Color.translate(Main.getPrefix() + "&cYour inventory is full."));
                return;
            }
            player.getInventory().addItem(item);
            player.sendMessage(Color.translate(Main.getPrefix() + "&aItem has been added to your inventory."));
        } else {
            player.sendMessage(Color.translate(Main.getPrefix() + "&cYou cannot request more than one item at a time."));
        }
    }

}
