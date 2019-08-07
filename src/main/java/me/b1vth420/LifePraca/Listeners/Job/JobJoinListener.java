package me.b1vth420.LifePraca.Listeners.Job;

import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Listeners.Events.JobJoinEvent;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class JobJoinListener implements Listener{

    @EventHandler
    public void onJoin(JobJoinEvent e){
        e.setCancelled(false);

        JobUser ju = JobUser.get(e.getPlayer());

        String info = Main.getInst().getSQLManager().getInfo(e.getPlayer());

        Main.getInst().getSQLManager().updatePlayer(ju);

        ItemStack is = new ItemStack(Material.NAME_TAG);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("Odznaka");
        String level = Config.getInst().badgeLevels.get(ju.getLevels().get(ju.getJob()).getKey());
        List<String> lore = new ArrayList<>();
        for(String s : Config.getInst().badgeLore){
            lore.add(ChatUtil.chat(ChatUtil.fixJobsLore(s, info.split(" ")[0], Integer.parseInt(info.split(" ")[1]), level, ju)));
        }
        im.setLore(lore);
        is.setItemMeta(im);


        ItemStack palka = new ItemStack(Material.STICK);
        ItemMeta imx = palka.getItemMeta();
        imx.setDisplayName(ChatUtil.chat("&4Palka policyjna"));
        palka.setItemMeta(imx);

        ItemStack apteczka = new ItemStack(Material.CAKE);
        ItemMeta Apteczka = apteczka.getItemMeta();
        Apteczka.setDisplayName(ChatUtil.chat("&2Apteczka"));
        apteczka.setItemMeta(Apteczka);

        if(ju.getJob().getJobType().equals(jobType.PREMIUM) && ju.getJob().getName().equalsIgnoreCase("medyk")){
            e.getPlayer().getInventory().addItem(apteczka);
        }

        if(ju.getJob().getJobType().equals(jobType.PREMIUM) && ju.getJob().getName().equalsIgnoreCase("policjant")){
            e.getPlayer().getInventory().addItem(palka);
            e.getPlayer().getInventory().addItem(is);
        }
    }
}
