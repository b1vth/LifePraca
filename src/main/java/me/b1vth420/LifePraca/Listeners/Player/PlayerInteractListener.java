package me.b1vth420.LifePraca.Listeners.Player;

import me.b1vth420.LifePraca.Objects.JobUser;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        JobUser ju = JobUser.get(e.getPlayer());

        if(!ju.hasJob()) return;

        if(ju.isBuilding() && !e.isCancelled()){
            if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                e.getPlayer().getInventory().addItem(new ItemStack(e.getClickedBlock().getType()));
                e.getClickedBlock().setType(Material.AIR);
            }
        }
    }
}
