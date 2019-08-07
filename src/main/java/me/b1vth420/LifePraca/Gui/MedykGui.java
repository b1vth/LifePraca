package me.b1vth420.LifePraca.Gui;

import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class MedykGui {

    public static InventoryView medykGui(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatUtil.chat("&aMenu medyka"));

        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        for (int i = 17; i < 27; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(10, ItemUtil.BuildItem(Material.CAKE, ChatUtil.chat("&cUlecz gracza")));
        inv.setItem(12, ItemUtil.BuildItem(Material.NAME_TAG, ChatUtil.chat("&cSprawdz dowod")));
        inv.setItem(14, ItemUtil.BuildItem(Material.BOOK, ChatUtil.chat("&cSprawdz prace")));
        inv.setItem(16, ItemUtil.BuildItem(Material.CHEST, ChatUtil.chat("&cPrzeszukaj")));

        return p.openInventory(inv);
    }
}
