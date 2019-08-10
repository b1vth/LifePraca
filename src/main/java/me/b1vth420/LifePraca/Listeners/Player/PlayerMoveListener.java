package me.b1vth420.LifePraca.Listeners.Player;

import me.b1vth420.LifePraca.Managers.JobUserManager;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.DeathUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener{
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if(JobUser.get(e.getPlayer()).isBrokenLeg())
            if(e.getPlayer().isSprinting()) e.getPlayer().setSprinting(false);

        if(DeathUtil.isDead(e.getPlayer())) e.setCancelled(true);

        for(Player p : JobUserManager.getFreezedPlayers().values()){
            if(p.equals(e.getPlayer())) e.setCancelled(true);
        }
    }

}
