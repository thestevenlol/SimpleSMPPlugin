package me.stevenlol.simplesmp.events;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MuteEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    private Player player; // he who bans
    private OfflinePlayer target;
    private String reason;
    private String date;

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public MuteEvent(String reason, OfflinePlayer target, Player player) {
        this.reason = reason;
        this.target = target;
        this.player = player;
        SimpleDateFormat patternFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.date = patternFormat.format(new Date());
        isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancel = isCancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public OfflinePlayer getTarget() {
        return target;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
