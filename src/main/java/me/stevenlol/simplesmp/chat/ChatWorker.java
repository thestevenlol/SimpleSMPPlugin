package me.stevenlol.simplesmp.chat;

import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import me.stevenlol.simplesmp.utilities.TimeFormatFromSeconds;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ChatWorker implements Listener {

    private String getPrefixes(Player player) {
        Chat chat = Main.getChat();
        return chat.getPlayerPrefix(player);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        e.setCancelled(true);
        SQLUtils.isMuted(player, isMuted -> {

            if (isMuted) {
                SQLUtils.getMuteInformation(player, infoString -> {
                    String[] infos = infoString.split("---");
                    String reason = infos[2];
                    String muterUUID = infos[0];
                    String date = infos[1];
                    OfflinePlayer muter = Bukkit.getOfflinePlayer(UUID.fromString(muterUUID));
                    int durationRemaining = Integer.parseInt(infos[3]);
                    player.sendMessage(Color.translate("&8&m==========&8[&c&l Muted &8]&8&m==========\n" +
                            "&c&lReason:&7 " + reason +"\n" +
                            "&c&lMuted by:&7 " + muter.getName() + "\n" +
                            "&c&lDate:&7 " + date + "\n" +
                            "&c&lTime remaining:&7 " + TimeFormatFromSeconds.time(durationRemaining) + "\n" +
                            "&8&m==========&8[&c&l Muted &8]&8&m=========="));
                });
                return;
            }

            SQLUtils.getChannel(player, channel -> {
                String prefix = getPrefixes(player);

                TextComponent prefixesAndName = new TextComponent(Color.translate(prefix + player.getDisplayName()));
                TextComponent barrier = new TextComponent(Color.translate(" &7|&r "));
                TextComponent message = new TextComponent(e.getMessage());

                prefixesAndName.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Color.translate("&eClick to tag player in chat!")).create()));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Color.translate("&cClick to report message.")).create()));

                switch (channel) {
                    case "global":
                        Bukkit.getOnlinePlayers().forEach(online -> online.spigot().sendMessage(prefixesAndName, barrier, message));
                        break;
                    case "staff":
                        message.setColor(ChatColor.RED);
                        TextComponent staffChannelPrefix = new TextComponent(Color.translate("&c[STAFF]&r "));
                        Bukkit.getOnlinePlayers().stream().filter(online -> online.hasPermission("ssmp.staff"))
                                .forEach(online -> online.spigot().sendMessage(staffChannelPrefix, prefixesAndName, barrier, message));
                        break;
                }
            });

        });

    }

}
