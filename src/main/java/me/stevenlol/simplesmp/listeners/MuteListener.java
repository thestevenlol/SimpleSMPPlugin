package me.stevenlol.simplesmp.listeners;

import lombok.SneakyThrows;
import me.stevenlol.simplesmp.events.MuteEvent;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MuteListener implements Listener {

    @EventHandler
    @SneakyThrows
    public void onMute(MuteEvent e) {
        Player player = e.getPlayer();
        OfflinePlayer target = e.getTarget();
        String reason = e.getReason();
        String date = e.getDate();

        Bukkit.broadcastMessage(Color.translate("&c" + target.getName() + "&f has been muted by &c" + player.getName() + "&f for:&c ") + reason);

        SQLUtils.getTotalMutes(target, total -> {
            switch (total) {
                case 0:
                    SQLUtils.addMute(target, reason, 60*5, player, date);
                    break;
                case 1:
                    SQLUtils.addMute(target, reason, 60*10, player, date);
                    break;
                case 2:
                    SQLUtils.addMute(target, reason, 60*20, player, date);
                    break;
                case 3:
                    SQLUtils.addMute(target, reason, 60*60, player, date);
                    break;
                case 4:
                    SQLUtils.addMute(target, reason, 60*60*3, player, date);
                    break;
                case 5:
                    SQLUtils.addMute(target, reason, 60*60*24, player, date);
                    break;
                case 6:
                    SQLUtils.addMute(target, reason, 60*60*24*7, player, date);
                    break;
                case 7:
                    SQLUtils.addMute(target, reason, -1, player, date);
                    break;
            }

        });

    }

}

// 5m
// 10m
// 20m
// 1hr
// 3hr
// 24hr
// 1 week
// perm

