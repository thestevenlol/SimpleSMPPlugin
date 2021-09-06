package me.stevenlol.simplesmp.listeners;

import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.SQLUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerInfoListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SQLUtils.exists(player, exists -> {
            Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
                String pattern = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                String date = sdf.format(new Date());
                if (!exists) {
                    try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("INSERT INTO ssmp_player_info (UUID, CHANNEL, JOIN_DATE, TELEPORTS, MESSAGES, ANNOUNCEMENTS) VALUES (?,?,?,?,?,?)")) {
                        statement.setString(1, player.getUniqueId().toString());
                        statement.setString(2, "global");
                        statement.setString(3, date);
                        statement.setBoolean(4, true);
                        statement.setBoolean(5, true);
                        statement.setBoolean(6, true);
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println("An error occurred with the PlayerInfoListener SQL update.");
                        if (Main.getPlugin().getConfig().getBoolean("sql-debug-on-launch")) e.printStackTrace();
                    }
                }
            });
        });
    }

}
