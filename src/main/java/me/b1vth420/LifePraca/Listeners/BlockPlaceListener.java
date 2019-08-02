package me.b1vth420.LifePraca.Listeners;

import me.b1vth420.LifePraca.Objects.JobUser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        JobUser ju = JobUser.get(e.getPlayer());
        if(!ju.hasJob()) return;
        if(ju.isBuilding()){
            if(!e.getPlayer().getInventory().containsAtLeast(new ItemStack(e.getItemInHand().getType()), 20)){
                e.getPlayer().getInventory().addItem(new ItemStack(e.getItemInHand().getType(), 21-e.getItemInHand().getAmount()));
            }
        }
    }
}
