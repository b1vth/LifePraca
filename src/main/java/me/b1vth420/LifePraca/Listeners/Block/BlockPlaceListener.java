package me.b1vth420.LifePraca.Listeners.Block;

import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        JobUser ju = JobUser.get(e.getPlayer());

        ItemStack apteczka = new ItemStack(Material.CAKE);
        ItemMeta Apteczka = apteczka.getItemMeta();
        Apteczka.setDisplayName(ChatUtil.chat("&2Apteczka"));
        apteczka.setItemMeta(Apteczka);

        if(e.getItemInHand().equals(apteczka)) e.setCancelled(true);
        if(!ju.hasJob()) return;
        if(ju.isBuilding()){
            if(!e.getPlayer().getInventory().containsAtLeast(new ItemStack(e.getItemInHand().getType()), 20)){
                e.getPlayer().getInventory().addItem(new ItemStack(e.getItemInHand().getType(), 21-e.getItemInHand().getAmount()));
            }
        }
    }
}
