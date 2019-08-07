package me.b1vth420.LifePraca.Gui;

import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Managers.JobManager;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PracaGui {

    public static InventoryView jobsTypes(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatUtil.chat("&cTypy prac"));

        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(10, ItemUtil.BuildItem(Material.GOLD_PICKAXE, ChatUtil.chat("&aPrace Legalne")));
        inv.setItem(12, ItemUtil.BuildItem(Material.CAULDRON_ITEM, ChatUtil.chat("&cPrace Nielegalne")));
        inv.setItem(14, ItemUtil.BuildItem(Material.DIAMOND_BLOCK, ChatUtil.chat("&bPrace PREMIUM")));
        inv.setItem(16, ItemUtil.BuildItem(Material.MUSHROOM_SOUP, ChatUtil.chat("&4Bezrobotny")));

        for (int i = 17; i < 27; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        return p.openInventory(inv);
    }

    public static InventoryView premiumJobsx(Player p) {
        Inventory inv = Bukkit.createInventory(null, 36, ChatUtil.chat("&aUrzad Pracy: Prace Premium"));
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(17, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        inv.setItem(18, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));

        for (int i = 26; i < 36; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        int i = 10;
        for (Job j : JobManager.getJobs().values()) {
            if(j.getJobType().equals(jobType.PREMIUM)) {
                ItemStack is = j.getJobItem();
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatUtil.chat("&a" + j.getName()));
                im.setLore(j.getJobDescription());
                is.setItemMeta(im);
                if(inv.getItem(i) != null) i++;
                inv.setItem(i, is);
                i+=2;
            }
        }


        return p.openInventory(inv);
    }

    public static InventoryView illlegalJobsx(Player p) {
        Inventory inv = Bukkit.createInventory(null, 36, ChatUtil.chat("&aUrzad Pracy: Prace nielegalne"));
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(17, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        inv.setItem(18, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));

        for (int i = 26; i < 36; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        int i = 10;
        for (Job j : JobManager.getJobs().values()) {
            if(j.getJobType().equals(jobType.ILLEGAL)) {
                ItemStack is = j.getJobItem();
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatUtil.chat("&a" + j.getName()));
                im.setLore(j.getJobDescription());
                is.setItemMeta(im);
                if(inv.getItem(i) != null) i++;
                inv.setItem(i, is);
                i+=2;
            }
        }


        return p.openInventory(inv);
    }

    public static InventoryView legalJobsx(Player p) {
        Inventory inv = Bukkit.createInventory(null, 36, ChatUtil.chat("&aUrzad Pracy: Prace Legalne"));
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(17, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        inv.setItem(18, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));

        for (int i = 26; i < 36; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        int i = 10;
        for (Job j : JobManager.getJobs().values()) {
            if(j.getJobType().equals(jobType.LEGAL)) {
                ItemStack is = j.getJobItem();
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatUtil.chat("&a" + j.getName()));
                im.setLore(j.getJobDescription());
                is.setItemMeta(im);
                if(inv.getItem(i) != null) i++;
                inv.setItem(i, is);
                i+=2;
            }
        }


        return p.openInventory(inv);
    }

    public static InventoryView premiumJobs(Player p) {
        Inventory inv = Bukkit.createInventory(null, 36, ChatUtil.chat("&aPrace Premium"));
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(17, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        inv.setItem(18, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));

        for (int i = 26; i < 36; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        int i = 10;
        for (Job j : JobManager.getJobs().values()) {
            if(j.getJobType().equals(jobType.PREMIUM)) {
                ItemStack is = j.getJobItem();
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatUtil.chat("&a" + j.getName()));
                im.setLore(j.getJobDescription());
                is.setItemMeta(im);
                if(inv.getItem(i) != null) i++;
                inv.setItem(i, is);
                i+=2;
            }
        }


        return p.openInventory(inv);
    }

    public static InventoryView illlegalJobs(Player p) {
        Inventory inv = Bukkit.createInventory(null, 36, ChatUtil.chat("&aPrace Nielegalne"));
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(17, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        inv.setItem(18, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));

        for (int i = 26; i < 36; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        int i = 10;
        for (Job j : JobManager.getJobs().values()) {
            if(j.getJobType().equals(jobType.ILLEGAL)) {
                ItemStack is = j.getJobItem();
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatUtil.chat("&a" + j.getName()));
                im.setLore(j.getJobDescription());
                is.setItemMeta(im);
                if(inv.getItem(i) != null) i++;
                inv.setItem(i, is);
                i+=2;
            }
        }


        return p.openInventory(inv);
    }

    public static InventoryView legalJobs(Player p) {
        Inventory inv = Bukkit.createInventory(null, 36, ChatUtil.chat("&aPrace Legalne"));
        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(17, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        inv.setItem(18, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));

        for (int i = 26; i < 36; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        int i = 10;
        for (Job j : JobManager.getJobs().values()) {
            if(j.getJobType().equals(jobType.LEGAL)) {
                ItemStack is = j.getJobItem();
                ItemMeta im = is.getItemMeta();
                im.setDisplayName(ChatUtil.chat("&a" + j.getName()));
                im.setLore(j.getJobDescription());
                is.setItemMeta(im);
                if(inv.getItem(i) != null) i++;
                inv.setItem(i, is);
                i+=2;
            }
        }


        return p.openInventory(inv);
    }

    public static InventoryView jobsTypesx(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatUtil.chat("&aUrzad Pracy: Typy prac"));

        for (int i = 0; i < 10; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        inv.setItem(10, ItemUtil.BuildItem(Material.GOLD_PICKAXE, ChatUtil.chat("&aPrace Legalne")));
        inv.setItem(12, ItemUtil.BuildItem(Material.CAULDRON_ITEM, ChatUtil.chat("&cPrace nielegalne")));
        inv.setItem(14, ItemUtil.BuildItem(Material.DIAMOND_BLOCK, ChatUtil.chat("&bPrace PREMIUM")));
        inv.setItem(16, ItemUtil.BuildItem(Material.MUSHROOM_SOUP, ChatUtil.chat("&4Bezrobotny")));

        for (int i = 17; i < 27; i++) {
            inv.setItem(i, ItemUtil.BuildItem(Material.STAINED_GLASS_PANE, ChatUtil.chat(""), (short) 15));
        }

        return p.openInventory(inv);
    }
}
