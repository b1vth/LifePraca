package me.b1vth420.LifePraca.Listeners.Player;

import me.b1vth420.LifePraca.Objects.JobUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerJoinListener implements Listener{
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        new JobUser(e.getPlayer().getName(), e.getPlayer().getUniqueId(), null, 0, new HashMap<>());
    }
}
