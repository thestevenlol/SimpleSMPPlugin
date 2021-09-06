package me.stevenlol.simplesmp.scoreboards;

import me.stevenlol.simplesmp.Main;
import org.bukkit.Bukkit;

public class BalanceUpdater {
    public static void run() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getPlugin(), () -> {
            Bukkit.getOnlinePlayers().forEach(ScoreboardMain::updateBalance);
        }, 0, 20);
    }

}
