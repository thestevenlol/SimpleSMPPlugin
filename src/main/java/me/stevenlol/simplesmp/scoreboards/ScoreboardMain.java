package me.stevenlol.simplesmp.scoreboards;

import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

public class ScoreboardMain implements Listener {

    // title
    // bb
    // Channel

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        makeScoreboard(e.getPlayer());
        Bukkit.getOnlinePlayers().forEach(ScoreboardMain::updateScoreboard);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> { Bukkit.getOnlinePlayers().forEach(ScoreboardMain::updatePlayerCount); }, 10);

    }


    public static void updateScoreboard(Player player) {
        updatePlayerCount(player);
        updateChannel(player);
    }

    private static void updatePlayerCount(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        if (Bukkit.getOnlinePlayers().isEmpty()) {
            scoreboard.getTeam("playerCountTeam").setPrefix(ChatColor.YELLOW + "0");
        } else {
            scoreboard.getTeam("playerCountTeam").setPrefix(ChatColor.YELLOW + "" + Bukkit.getOnlinePlayers().size());
        }
    }

    private static void updateChannel(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        SQLUtils.getChannel(player, channel -> {
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getPlugin(), () -> {
                if (channel.equals("global")) {
                    scoreboard.getTeam("channelTeam").setPrefix(ChatColor.YELLOW + "Global");
                } else {
                    scoreboard.getTeam("channelTeam").setPrefix(ChatColor.RED + "Staff");
                }
            }, 10);
        });
    }

    public static void updateBalance(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Economy econ = Main.getEconomy();
        scoreboard.getTeam("balanceTeam").setPrefix(ChatColor.YELLOW + "" + econ.getBalance(player));
    }

    public static void makeScoreboard(Player player) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("SSMPScoreboard", "dummy", Color.translate("&e&oSimpleSMP"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score playerCount = objective.getScore(Color.translate("&7Total players:"));
        playerCount.setScore(18);
        Team playerCountTeam = scoreboard.registerNewTeam("playerCountTeam");
        playerCountTeam.addEntry(ChatColor.RED + "" + ChatColor.WHITE);
        playerCountTeam.setPrefix(ChatColor.YELLOW + "" + Bukkit.getOnlinePlayers().size());
        objective.getScore(ChatColor.RED + "" + ChatColor.WHITE).setScore(17);

        Score blank0 = objective.getScore(" ");
        blank0.setScore(16);

        SQLUtils.getChannel(player, channel -> {
            Team channelTeam = scoreboard.registerNewTeam("channelTeam");
            channelTeam.addEntry(ChatColor.BLACK + "" + ChatColor.WHITE);
            Score score = objective.getScore(Color.translate("&7Current channel:"));
            score.setScore(15);
            if (channel.equals("global")) {
                channelTeam.setPrefix(ChatColor.YELLOW + channel);
                objective.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(14);
            } else if (channel.equals("staff")) {
                channelTeam.setPrefix(ChatColor.YELLOW + channel);
                objective.getScore(ChatColor.BLACK + "" + ChatColor.WHITE).setScore(14);
            }
        });

        /*
        Score blank1 = objective.getScore(" ");
        blank1.setScore(13);

        Economy econ = Main.getEconomy();
        Score balance = objective.getScore(Color.translate("&7Balance:"));
        balance.setScore(12);
        Team balanceTeam = scoreboard.registerNewTeam("balanceTeam");
        balanceTeam.addEntry(ChatColor.DARK_PURPLE + "" + ChatColor.WHITE);
        balanceTeam.setPrefix(ChatColor.YELLOW + "" + econ.getBalance(player));
        objective.getScore(ChatColor.DARK_PURPLE + "" + ChatColor.WHITE).setScore(11);
         */

        player.setScoreboard(scoreboard);
    }

}
