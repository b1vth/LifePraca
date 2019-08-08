package me.b1vth420.LifePraca.Listeners.Inventory;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Managers.BuildingArenaManager;
import me.b1vth420.LifePraca.Objects.BuildingArea;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Objects.PatternArena;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BudowaInventoryClickListener implements Listener{

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory().getName().equals(ChatUtil.chat("&cPoziomy budowy"))){
            e.setCancelled(true);

            Player p = (Player) e.getWhoClicked();
            JobUser ju = JobUser.get(p);
            int level = ju.getLevels().get(ju.getJob()).getKey();

            if(level >= e.getSlot()+1)
                teleportToWork(p, ju, e.getSlot() + 1);
            else p.sendMessage(ChatUtil.chat("&4Blad!&c Nie masz odpowiedniego poziomu budowania!"));
        }
    }

    private void teleportToWork(Player p, JobUser ju, int level){
        p.closeInventory();
        BuildingArea ba = BuildingArenaManager.getEmpty(level);
        if(ba == null){
            p.sendMessage(ChatUtil.chat("&4Blad! &cW tej chwili nie ma miejsca na budowie!"));
            return;
        }
        PatternArena pa = ba.getPa();
        ju.setBuildingArea(ba);
        p.teleport(pa.getCenter());
        p.sendTitle(ChatUtil.chat("&2Praca"), ChatUtil.chat(Lang.getInst().buildingTeleportMessage), 20, 60, 20);
        ba.setEmpty(false);
        pa.fill();
        InventoryUtil.storeAndClearInventory(p);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
            public void run() {
                ju.setBuilding(true);
                for(Material m : pa.getBlocks()){
                    p.getInventory().addItem(new ItemStack(m, 20));
                }
                p.teleport(ba.getCenter());
                p.setLevel(pa.getSchema().getTime());
            }
        }, 200L);
    }

}
