package me.b1vth420.LifePraca.Listeners.Inventory;

import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Gui.PracaGui;
import me.b1vth420.LifePraca.Listeners.Events.JobJoinEvent;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener{

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        JobUser ju = JobUser.get(p);
        if(e.getClickedInventory() == null || e.getCurrentItem() == null) return;
        if(e.getClickedInventory().getName().equals(ChatUtil.chat("&cTypy prac"))){
            if(e.getSlot() == 10) PracaGui.legalJobs(p);
            if(e.getSlot() == 12) PracaGui.illlegalJobs(p);
            if(e.getSlot() == 14) PracaGui.premiumJobs(p);
            if(e.getSlot() == 16) {
                p.closeInventory();
                ju.setJob(null);
            }
            e.setCancelled(true);
        }

        if(e.getClickedInventory().getName().equals(ChatUtil.chat("&aPrace Premium"))) e.setCancelled(true);
        if(e.getClickedInventory().getName().equals(ChatUtil.chat("&aPrace Nielegalne"))) e.setCancelled(true);
        if(e.getClickedInventory().getName().equals(ChatUtil.chat("&aPrace Legalne"))) e.setCancelled(true);

        if(e.getClickedInventory().getName().equals(ChatUtil.chat("&aUrzad Pracy: Typy prac"))){
            if(e.getSlot() == 10) PracaGui.legalJobsx(p);
            if(e.getSlot() == 12) PracaGui.illlegalJobsx(p);
            if(e.getSlot() == 14) PracaGui.premiumJobsx(p);
            if(e.getSlot() == 16) {
                p.closeInventory();
                ju.setJob(null);
            }
            e.setCancelled(true);
        }

        if(e.getClickedInventory().getName().equals(ChatUtil.chat("&aUrzad Pracy: Prace Legalne"))) {
            e.setCancelled(true);
            Job j = Job.get(e.getCurrentItem(), jobType.LEGAL);
            if(j == null) return;

            ju.setJob(j);
            JobJoinEvent eve = new JobJoinEvent(p);
            Bukkit.getPluginManager().callEvent(eve);
            p.sendMessage(ChatUtil.chat("&7Dolaczyles do pracy &a" + ju.getJob().getName()));
        }

        if(e.getClickedInventory().getName().equals(ChatUtil.chat("&aUrzad Pracy: Prace nielegalne"))) {
            e.setCancelled(true);
            Job j = Job.get(e.getCurrentItem(), jobType.ILLEGAL);
            if(j == null) return;

            ju.setJob(j);
            JobJoinEvent eve = new JobJoinEvent(p);
            Bukkit.getPluginManager().callEvent(eve);
            p.sendMessage(ChatUtil.chat("&7Dolaczyles do pracy &a" + ju.getJob().getName()));
        }

        if(e.getClickedInventory().getName().equals(ChatUtil.chat("&aUrzad Pracy: Prace Premium"))) {
            e.setCancelled(true);
            Job j = Job.get(e.getCurrentItem(), jobType.PREMIUM);
            if(j == null) return;

            ju.setJob(j);
            if(j.getName().equalsIgnoreCase("policjant")) ju.setJoinTime(System.currentTimeMillis());
            JobJoinEvent eve = new JobJoinEvent(p);
            Bukkit.getPluginManager().callEvent(eve);
            p.sendMessage(ChatUtil.chat("&7Dolaczyles do pracy &a" + ju.getJob().getName()));
        }
    }
}
