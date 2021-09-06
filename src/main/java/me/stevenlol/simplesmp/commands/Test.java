package me.stevenlol.simplesmp.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import me.stevenlol.simplesmp.utilities.TimeFormatFromSeconds;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Test extends BaseCommand {

    @CommandAlias("test")
    public void test(Player player) {
        SQLUtils.getMuteInformation(player, infoString -> {
            String[] infos = infoString.split("---");
            String reason = infos[2];
            String muterUUID = infos[0];
            String date = infos[1];
            OfflinePlayer muter = Bukkit.getOfflinePlayer(UUID.fromString(muterUUID));
            int durationRemaining = Integer.parseInt(infos[3]);
            Bukkit.getScheduler().runTask(Main.getPlugin(), () -> {
                TextComponent message = new TextComponent(Color.translate(Main.getPrefix() + "&cYou are currently muted.&r "));
                TextComponent hoverForInfo = new TextComponent(Color.translate("&7[&c&oHover for more information&7]"));
                hoverForInfo.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(Color.translate("&7You were muted by&c " + muter.getName()
                                + "\n&7For the reason: &c" + reason +
                                "\n&7On the date:&c " + date +
                                "\n&7Time remaining:&C " + TimeFormatFromSeconds.time(durationRemaining)))
                                .create()));

                player.spigot().sendMessage(message, hoverForInfo);
            });
        });
    }

}
