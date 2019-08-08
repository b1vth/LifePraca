package me.b1vth420.LifePraca.Listeners.Player;

import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        JobUser ju = JobUser.get(e.getPlayer());

        if(!ju.hasJob()) return;

        if(e.getPlayer().isSneaking()){
            for(Map.Entry<ItemStack, Map.Entry<List<String>, Map.Entry<List<PotionEffect>, Map.Entry<Integer, Integer>>>> map : Main.getInst().getDrugs().values()){
                ItemStack is = map.getKey().clone();
                e.getPlayer().getInventory().remove(is);
                if(ItemUtil.checkItem(is, e.getItem())){
                    for(String s : map.getValue().getKey())
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
                    for(PotionEffect pe : map.getValue().getValue().getKey()){
                        e.getPlayer().removePotionEffect(pe.getType());
                        e.getPlayer().addPotionEffect(pe);
                    }
                }
            }
        }

        if(ju.isBuilding() && !e.isCancelled()){
            if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                e.getPlayer().getInventory().addItem(new ItemStack(e.getClickedBlock().getType()));
                e.getClickedBlock().setType(Material.AIR);
            }
        }
    }
}
