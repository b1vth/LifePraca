package me.b1vth420.LifePraca.Listeners.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LevelUpEvent extends Event implements Cancellable {

    private final Player player;
    private boolean isCancelled;
    private int level;
    private static final HandlerList handlers  = new HandlerList();

    public LevelUpEvent(Player p, int level){
        this.isCancelled = false;
        this.player = p;
        this.level = level;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = true;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() { return handlers; }

    public Player getPlayer(){return this.player; }

    public int getLevel() { return level; }
}
