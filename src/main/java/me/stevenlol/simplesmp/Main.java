package me.stevenlol.simplesmp;

import co.aikar.commands.PaperCommandManager;
import me.stevenlol.simplesmp.chat.ChatWorker;
import me.stevenlol.simplesmp.commands.ChannelCommand;
import me.stevenlol.simplesmp.commands.SSMP;
import me.stevenlol.simplesmp.commands.gamemode.GMA;
import me.stevenlol.simplesmp.commands.gamemode.GMC;
import me.stevenlol.simplesmp.commands.gamemode.GMS;
import me.stevenlol.simplesmp.commands.gamemode.GMSP;
import me.stevenlol.simplesmp.commands.homes.HomeCommand;
import me.stevenlol.simplesmp.commands.homes.SetHomeCommand;
import me.stevenlol.simplesmp.commands.moderation.BanCommand;
import me.stevenlol.simplesmp.commands.moderation.ClearChatCommand;
import me.stevenlol.simplesmp.commands.moderation.MuteCommand;
import me.stevenlol.simplesmp.commands.utilcommands.ClearInventoryCommand;
import me.stevenlol.simplesmp.commands.utilcommands.ItemCommand;
import me.stevenlol.simplesmp.listeners.BanListener;
import me.stevenlol.simplesmp.listeners.MuteListener;
import me.stevenlol.simplesmp.listeners.PlayerInfoListener;
import me.stevenlol.simplesmp.scoreboards.ScoreboardMain;
import me.stevenlol.simplesmp.tasks.MuteTicker;
import me.stevenlol.simplesmp.utilities.SQL;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private static SQL sql;
    private static Chat chat = null;
    private static Economy econ = null;
    private static String prefix;
    private PaperCommandManager manager;

    @Override
    public void onEnable() {
        plugin = this;
        prefix = getConfig().getString("chat-prefix");
        manager = new PaperCommandManager(this);

        sql = new SQL();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        sql.connect();

        registerCommands();
        registerListeners();

        setupChat();
        setupEconomy();

        MuteTicker.run();
        //BalanceUpdater.run();

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            Bukkit.getOnlinePlayers().forEach(ScoreboardMain::updateScoreboard);
        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        manager.registerCommand(new SSMP());
        manager.registerCommand(new BanCommand());
        manager.registerCommand(new MuteCommand());
        manager.registerCommand(new ChannelCommand());
        manager.registerCommand(new GMC());
        manager.registerCommand(new GMS());
        manager.registerCommand(new GMSP());
        manager.registerCommand(new GMA());
        manager.registerCommand(new ClearInventoryCommand());
        manager.registerCommand(new ClearChatCommand());
        manager.registerCommand(new ItemCommand());
        manager.registerCommand(new SetHomeCommand());
        manager.registerCommand(new HomeCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerInfoListener(), this);
        getServer().getPluginManager().registerEvents(new BanListener(), this);
        getServer().getPluginManager().registerEvents(new MuteListener(), this);
        getServer().getPluginManager().registerEvents(new ChatWorker(), this);
        getServer().getPluginManager().registerEvents(new ScoreboardMain(), this);
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }


    public static Main getPlugin() {
        return plugin;
    }

    public static Chat getChat() { return chat; }
    public static Economy getEconomy() { return econ; }
    public static SQL getSql() { return sql; }
    public static String getPrefix() { return prefix; }
}
