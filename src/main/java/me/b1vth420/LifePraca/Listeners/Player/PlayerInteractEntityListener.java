package me.b1vth420.LifePraca.Listeners.Player;

import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Gui.MedykGui;
import me.b1vth420.LifePraca.Gui.PolicjantGui;
import me.b1vth420.LifePraca.Gui.PracaGui;
import me.b1vth420.LifePraca.Listeners.Events.LevelUpEvent;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.DeathUtil;
import me.b1vth420.LifePraca.Utils.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.AbstractMap;

public class PlayerInteractEntityListener implements Listener{
    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        if(e.getRightClicked() instanceof Villager){
            Villager v = (Villager) e.getRightClicked();
            if(v.getName().equals("Praca")){
                e.setCancelled(true);
                PracaGui.jobsTypesx(e.getPlayer());
            }
        }

        if(e.getRightClicked() instanceof Player) {
            Player p = (Player) e.getPlayer();
            JobUser ju = JobUser.get(e.getPlayer());
            Job j = ju.getJob();
            int level = ju.getLevels().get(j).getKey();

            if (!ju.hasJob()) return;

            ju.setCheeked((Player) e.getRightClicked());

            if (DeathUtil.isSleeping((Player) e.getRightClicked()))
                p.openInventory(((Player) e.getRightClicked()).getInventory());

            if (ju.getJob().getJobType().equals(jobType.PREMIUM) && ju.getJob().getName().equalsIgnoreCase("medyk") && p.getItemInHand().equals(Main.getInst().getSavedItems().get("Medyk")))
                MedykGui.medykGui(p);

            if (ju.getJob().getJobType().equals(jobType.PREMIUM) && ju.getJob().getName().equalsIgnoreCase("policjant") && p.getItemInHand().equals(Main.getInst().getSavedItems().get("Policja")))
                PolicjantGui.policjantGui(p);

            JobUser ju2 = JobUser.get(ju.getCheeked());

            if(p.getItemInHand().equals(Main.getInst().getSavedItems().get("Zlodziej")) && ju.getJob().getName().equalsIgnoreCase("zlodziej") || ju.getJob().getName().equalsIgnoreCase("zlodzieii"))
                steal(ju2, ju);

            if (ju.getLevels().get(j).getValue() >= level * level * 100) {
                LevelUpEvent ev = new LevelUpEvent(e.getPlayer(), ju.getLevels().get(ju.getJob()).getKey());
                Bukkit.getPluginManager().callEvent(ev);
            }
        }
    }

    private void steal(JobUser ju, JobUser ju2){
        DeathUtil.toSteal.putIfAbsent(ju.getName(), ju.getPlayer());
        ChatUtil.sendActionBar(ju.getPlayer(), ChatUtil.chat("&4Uwaga! &cKtos chce cie okrasc! /obron sie"));
        Bukkit.getScheduler().runTaskLater(Main.getInst(), new Runnable() {
            public void run() {
                if (DeathUtil.containsSteel(ju.getPlayer())) {
                    ChatUtil.sendActionBar(ju.getPlayer(), ChatUtil.chat("&4Uwaga! &cNie obroniles sie przed kradzieza!"));

                    int percentToTake = 0;
                    if(ju.getLevels().get(ju.getJob()).getKey() > 1) {
                        int min = ju.getJob().getLevels().get(ju.getLevels().get(ju.getJob()).getKey() - 1).intValue();
                        int max = ju.getJob().getLevels().get(ju.getLevels().get(ju.getJob()).getKey()).intValue();
                        percentToTake = RandomUtil.getRandInt(min, max);
                    } else {
                        int min = 5;
                        int max = ju.getJob().getLevels().get(ju.getLevels().get(ju.getJob()).getKey()).intValue();
                        percentToTake = RandomUtil.getRandInt(min, max);
                    }
                    double toTake = ju.getMoney()-(ju.getMoney()/percentToTake);
                    ju2.setMoney(ju2.getMoney()+toTake);
                    ju.setMoney(ju.getMoney()-toTake);
                    ju2.getLevels().replace(ju.getJob(), new AbstractMap.SimpleEntry<>(ju.getLevels().get(ju.getJob()).getKey(), ju.getLevels().get(ju.getJob()).getValue() + (int) toTake/2));
                } else {
                    ChatUtil.sendActionBar(ju.getPlayer(), ChatUtil.chat("&2Uwaga! &aObroniles sie przed kradzieza!"));
                }
            }
        },5*20);
    }

}
