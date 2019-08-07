package me.b1vth420.LifePraca.Gui;

import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class BudowaGui {

    public static InventoryView budowa(Player p){
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, ChatUtil.chat("&cPoziomy budowy"));
        JobUser ju = JobUser.get(p);
        int level = ju.getLevels().get(ju.getJob()).getKey();

        for(int i = 1; i<6; i++){
            if(level>=i) inv.setItem(i-1, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat("&aPoziom " +i), (short) 13));
                else inv.setItem(i-1, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat("&cPoziom " +i), (short) 14));
        }

        return p.openInventory(inv);
    }
}
