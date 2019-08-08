package me.b1vth420.LifePraca.Listeners.Block;

import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ItemUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

public class DilerBlockPlaceListener implements Listener{
    @EventHandler
    public void onBreak(BlockPlaceEvent e){

        JobUser ju = JobUser.get(e.getPlayer());

        Location l = new Location(e.getBlockPlaced().getWorld(), e.getBlockPlaced().getLocation().getBlockX(), e.getBlockPlaced().getLocation().getBlockY()+2, e.getBlockPlaced().getLocation().getBlockZ());
        for(Map.Entry<ItemStack, Map.Entry<List<String>, Map.Entry<List<PotionEffect>, Map.Entry<Integer, Integer>>>> map : Main.getInst().getDrugs().values()) {
            if(!(ItemUtil.checkItem(e.getItemInHand(), map.getKey()))) return;
            if (e.getPlayer().getWorld().getBlockAt(l).getType().equals(Material.REDSTONE_LAMP_ON) && ju.getJob().getName().equalsIgnoreCase("diler") && ju.getLevels().get(ju.getJob()).getKey() >= map.getValue().getValue().getValue().getKey()) {
                if (ItemUtil.checkItem(e.getItemInHand(), map.getKey())) {
                    Main.getInst().getSQLManager().updateDrugFarms(e.getBlockPlaced().getLocation(), e.getBlockPlaced().getType());
                    Main.getInst().getDrugFarms().add(e.getBlockPlaced().getLocation());
                } else e.setCancelled(true);
            } else e.setCancelled(true);
        }
    }
}
