package me.stevenlol.simplesmp.utilities;

import lombok.SneakyThrows;
import me.stevenlol.simplesmp.Main;
import me.stevenlol.simplesmp.utilities.Color;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {

    private final String host = Main.getPlugin().getConfig().getString("database.host");
    private final int port = Main.getPlugin().getConfig().getInt("database.port");
    private final String database = Main.getPlugin().getConfig().getString("database.database");
    private final String username = Main.getPlugin().getConfig().getString("database.username");
    private final String password = Main.getPlugin().getConfig().getString("database.password");
    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    public void connect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);

                PreparedStatement createPlayerInfoTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ssmp_player_info (" +
                        "UUID VARCHAR(100), " +
                        "CHANNEL VARCHAR(100), " +
                        "JOIN_DATE VARCHAR(100), " +
                        "TELEPORTS BOOLEAN, " +
                        "MESSAGES BOOLEAN, " +
                        "ANNOUNCEMENTS BOOLEAN)");

                PreparedStatement createPlayerMutesTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ssmp_player_mutes (" +
                        "UUID VARCHAR(100), " +
                        "REASON VARCHAR(100), " +
                        "DURATION VARCHAR(100), " +
                        "MUTER_UUID VARCHAR(100)," +
                        "DATE VARCHAR(100))");

                PreparedStatement createPlayerHomesTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ssmp_player_homes (" +
                        "UUID VARCHAR(100)," +
                        "X DOUBLE(200,30)," +
                        "Y DOUBLE(200,30)," +
                        "Z DOUBLE(200,30)," +
                        "YAW FLOAT(200,30)," +
                        "PITCH FLOAT(200,30)," +
                        "WORLD VARCHAR(255)," +
                        "NAME VARCHAR(255))");

                PreparedStatement createBankTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ssmp_player_banks (" +
                        "UUID VARCHAR(100)," +
                        "BANKACCOUNT DOUBLE(200,30)," +
                        "BALANCE DOUBLE(200,30))");

                PreparedStatement createNotesTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS ssmp_player_notes (" +
                        "UUID VARCHAR(100)," +
                        "NOTE VARCHAR(255)," +
                        "ID INT)");

                createPlayerInfoTable.executeUpdate();
                createPlayerMutesTable.executeUpdate();
                createPlayerHomesTable.executeUpdate();
                createBankTable.executeUpdate();
                createNotesTable.executeUpdate();

                System.out.println("Connected to database successfully.");
            } catch (SQLException e) {
                System.out.println(Color.translate("Plugin disabling due to an error. Is the SQL server running?"));
                System.out.println(Color.translate("If you want to see the error, please change the sql-debug-on-launch value in config to true."));
                if (Main.getPlugin().getConfig().getBoolean("sql-debug-on-launch")) e.printStackTrace();
                Bukkit.shutdown();
            }
        }
    }

    @SneakyThrows
    public void disconnect() {
        if (isConnected()) {
            connection.close();
        }
    }

    @SneakyThrows
    public Connection getConnection() {
        return connection;
    }
}
