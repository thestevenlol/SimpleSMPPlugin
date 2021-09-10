package me.stevenlol.simplesmp.listeners;

import me.stevenlol.simplesmp.events.BanEvent;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.Messages;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BanListener implements Listener {

    @EventHandler
    public void onBan(BanEvent e) {
        Player player = e.getPlayer();
        OfflinePlayer target = e.getTarget();
        String reason = e.getReason();
        Bukkit.broadcastMessage(Color.translate("&c" + target.getName() + "&f has been banned by &c" + player.getName() + "&f for:&c ") + reason);

        if (target.isOnline()) {
            target.getPlayer().kickPlayer(Color.translate(Messages.banMessage
                    .replace("{banner}", player.getName())
                    .replace("{date}", e.getDate()))
                    .replace("{reason}", reason));
        }


    }

}
