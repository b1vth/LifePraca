package me.b1vth420.LifePraca.Listeners.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class PlayerExpChangeListener implements Listener{
    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e){
        e.getPlayer().setExp(0);
        e.getPlayer().setLevel(0);
    }

}
