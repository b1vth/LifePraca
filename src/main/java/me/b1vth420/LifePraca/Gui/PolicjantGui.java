package me.b1vth420.LifePraca.Gui;

import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class PolicjantGui {

    public static InventoryView policjantGui(Player p){
        Inventory inv = Bukkit.createInventory(null, 36, ChatUtil.chat("&aMenu policyjne"));

        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(17, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        inv.setItem(18, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));

        for (int i = 26; i < 36; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(10, ItemUtil.BuildItem(Material.IRON_FENCE, ChatUtil.chat("&cZatrzymaj gracza")));
        inv.setItem(12, ItemUtil.BuildItem(Material.GOLD_INGOT, ChatUtil.chat("&cWypisz mandat")));
        inv.setItem(14, ItemUtil.BuildItem(Material.POISONOUS_POTATO, ChatUtil.chat("&cZmutuj")));
        inv.setItem(16, ItemUtil.BuildItem(Material.TRAP_DOOR, ChatUtil.chat("&cWyrzuc")));
        inv.setItem(20, ItemUtil.BuildItem(Material.NAME_TAG, ChatUtil.chat("&cSprawdz dowod")));
        inv.setItem(22, ItemUtil.BuildItem(Material.BOOK, ChatUtil.chat("&cSprawdz prace")));
        inv.setItem(24, ItemUtil.BuildItem(Material.CHEST, ChatUtil.chat("&cPrzeszukaj")));

        return p.openInventory(inv);
    }
}
