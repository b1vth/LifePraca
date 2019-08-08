package me.b1vth420.LifePraca.Listeners.Player;

import me.b1vth420.LifePraca.Utils.DeathUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener{
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        if(e.getMessage().equalsIgnoreCase("/obron sie")){
            for(Player p : DeathUtil.toSteal.values()){
                if(p.equals(e.getPlayer())){
                    DeathUtil.toSteal.remove(p.getName());
                }
            }
            e.setCancelled(true);
        }
    }

}
