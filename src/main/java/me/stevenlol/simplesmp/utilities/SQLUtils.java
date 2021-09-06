package me.stevenlol.simplesmp.utilities;

import me.stevenlol.simplesmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtils {

    public static void exists(OfflinePlayer player, SQLCallback<Boolean> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT * FROM ssmp_player_info WHERE UUID=?")) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet set = statement.executeQuery()) {
                        callback.onQueryDone(set.next());
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    // settings

    public static void setChannel(OfflinePlayer player, String channel) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
           try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("UPDATE ssmp_player_info SET CHANNEL=? WHERE UUID=?")) {
               statement.setString(1, channel);
               statement.setString(2, player.getUniqueId().toString());
               statement.executeUpdate();
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
        });
    }

    public static void getChannel(OfflinePlayer player, SQLCallback<String> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT CHANNEL FROM ssmp_player_info WHERE UUID=?")) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet set = statement.executeQuery()) {
                    if (set.next()) {
                        callback.onQueryDone(set.getString(1));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void setTeleports(OfflinePlayer player, boolean teleports) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("UPDATE ssmp_player_info SET TELEPORTS=? WHERE UUID=?")) {
                statement.setBoolean(1, teleports);
                statement.setString(2, player.getUniqueId().toString());
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void setMessages(OfflinePlayer player, boolean messages) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("UPDATE ssmp_player_info SET MESSAGES=? WHERE UUID=?")) {
                statement.setBoolean(1, messages);
                statement.setString(2, player.getUniqueId().toString());
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void setAnnouncements(OfflinePlayer player, boolean announcements) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("UPDATE ssmp_player_info SET ANNOUNCEMENTS=? WHERE UUID=?")) {
                statement.setBoolean(1, announcements);
                statement.setString(2, player.getUniqueId().toString());
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    // mutes

    public static void addMute(OfflinePlayer player, String reason, int duration, Player muter, String date) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("INSERT INTO ssmp_player_mutes (UUID, REASON, DURATION, MUTER_UUID, DATE) VALUES (?,?,?,?,?)")) {
                statement.setString(1, player.getUniqueId().toString());
                statement.setString(2, reason);
                statement.setInt(3, duration);
                statement.setString(4, muter.getUniqueId().toString());
                statement.setString(5, date);
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void isMuted(OfflinePlayer player, SQLCallback<Boolean> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT * FROM ssmp_player_mutes WHERE UUID=? AND DURATION > 0")) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet set = statement.executeQuery()) {
                        callback.onQueryDone(set.next());
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void getTotalMutes(OfflinePlayer player, SQLCallback<Integer> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT COUNT(*) FROM ssmp_player_mutes WHERE UUID=?")) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet set = statement.executeQuery()) {
                    if (set.next()) {
                        callback.onQueryDone(set.getInt(1));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void getMuteDurationRemaining(OfflinePlayer player, SQLCallback<Integer> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT DURATION FROM ssmp_player_mutes WHERE UUID=? AND DURATION > 0")) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet set = statement.executeQuery()) {
                    if (set.next()) {
                        callback.onQueryDone(set.getInt(1));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void tickMute(OfflinePlayer player, int duration) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("UPDATE ssmp_player_mutes SET DURATION=? WHERE UUID=? AND DURATION > 0")) {
                statement.setInt(1, duration - 1);
                statement.setString(2, player.getUniqueId().toString());
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void getMuteString(OfflinePlayer player, int index, SQLCallback<String> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT * FROM ssmp_player_mutes WHERE UUID=? AND DURATION > 0")) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet set = statement.executeQuery()) {
                    if (set.next()) {
                        callback.onQueryDone(set.getString(index));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void getMuteInt(OfflinePlayer player, int index, SQLCallback<Integer> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT * FROM ssmp_player_mutes WHERE UUID=? AND DURATION > 0")) {
                statement.setString(1, player.getUniqueId().toString());
                try (ResultSet set = statement.executeQuery()) {
                    if (set.next()) {
                        callback.onQueryDone(set.getInt(index));
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void getMuteInformation(OfflinePlayer player, SQLCallback<String> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
           try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT * FROM ssmp_player_mutes WHERE UUID=? AND DURATION > 0")) {
               statement.setString(1, player.getUniqueId().toString());
               try (ResultSet set = statement.executeQuery()) {
                   if (set.next()) {
                       String muterUUID = set.getString(4);
                       String date = set.getString(5);
                       String reason = set.getString(2);
                       int durationRemaining = set.getInt(3);
                       String finalString = muterUUID + "---" + date + "---" + reason + "---" + durationRemaining;
                       // uuid|||date|||reason|||durationRemaining
                       callback.onQueryDone(finalString);
                   }
               }
           } catch (SQLException throwables) {
               throwables.printStackTrace();
           }
        });
    }

    public static void setHomeLocation(Player player, String homeName) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("INSERT INTO ssmp_player_homes (UUID, X, Y, Z, YAW, PITCH, WORLD, NAME) VALUES (?,?,?,?,?,?,?,?)")) {
                statement.setString(1, player.getUniqueId().toString());
                statement.setDouble(2, player.getLocation().getX());
                statement.setDouble(3, player.getLocation().getY());
                statement.setDouble(4, player.getLocation().getZ());
                statement.setFloat(5, player.getLocation().getYaw());
                statement.setFloat(6, player.getLocation().getPitch());
                statement.setString(7, player.getWorld().getName());
                statement.setString(8, homeName);
                statement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    public static void getHomeLocation(Player player, String homeName, SQLCallback<Location> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), () -> {
            try (PreparedStatement statement = Main.getSql().getConnection().prepareStatement("SELECT * FROM ssmp_player_homes WHERE NAME = ?")) {
                statement.setString(1, homeName);
                ResultSet set = statement.executeQuery();
                if (set.next()) {
                    double X = set.getDouble(2);
                    double Y = set.getDouble(3);
                    double Z = set.getDouble(4);
                    float Yaw = set.getFloat(5);
                    float Pitch = set.getFloat(6);
                    String world = set.getString(7);
                    Location location = new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);
                            // uuid|||date|||reason|||durationRemaining
                    callback.onQueryDone(location);
                } else {
                    callback.onQueryDone(null);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

}
