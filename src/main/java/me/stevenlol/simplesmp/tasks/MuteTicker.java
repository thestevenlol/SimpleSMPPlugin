package me.stevenlol.simplesmp.tasks;

import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import org.bukkit.Bukkit;

public class MuteTicker {

    public static void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                SQLUtils.getMuteDurationRemaining(player, durationRemaining -> {
                    SQLUtils.tickMute(player, durationRemaining);
                });
            });
        }, 0, 20);
    }

}
