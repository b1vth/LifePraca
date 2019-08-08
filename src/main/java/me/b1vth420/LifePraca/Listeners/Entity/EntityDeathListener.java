package me.b1vth420.LifePraca.Listeners.Entity;

import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Listeners.Events.LevelUpEvent;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.DeathUtil;
import me.confuser.barapi.BarAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.AbstractMap;

public class EntityDeathListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent e) {

        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            p.setHealth(1);

            DeathUtil.playSleepAnimation(p);

            BarAPI.setMessage(p, ChatUtil.chat(Lang.getInst().bossBarOnDeath), Config.getInst().deathTime*60);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
                public void run() {
                    if (DeathUtil.isSleeping(p)) {
                        DeathUtil.stopSleepAnimation(p);
                        p.teleport(p.getWorld().getSpawnLocation());
                        BarAPI.removeBar(p);
                    }
                }
            }, Config.getInst().deathTime*60*1000);
        }

        if (e.getEntity().getKiller() != null) {
            JobUser ju = JobUser.get(e.getEntity().getKiller());

            if (!ju.hasJob()) return;
            if (!ju.getJob().getMoneyGivingKills().containsKey(e.getEntity().getType())) return;

            Job j = ju.getJob();

            ju.setMoney(ju.getMoney() + j.getLevels().get(ju.getLevels().get(j).getKey()));
            e.getEntity().getKiller().sendMessage(ChatUtil.chat(Lang.getInst().moneyAddMessage.replace("{MONEY}", String.valueOf(j.getLevels().get(ju.getLevels().get(j).getKey()))).replace("{BALANCE}", ChatUtil.formatDouble(ju.getMoney()))));
            ju.getLevels().put(j, new AbstractMap.SimpleEntry<>(ju.getLevels().get(j).getKey(), ju.getLevels().get(j).getValue() + j.getMoneyGivingKills().get(e.getEntityType())));

            if(ju.getLevels().get(j).getValue() >= ju.getLevels().get(j).getKey()* ju.getLevels().get(j).getKey() * 100) {
                LevelUpEvent ev = new LevelUpEvent(e.getEntity().getKiller(), ju.getLevels().get(j).getKey() +1);
                Bukkit.getPluginManager().callEvent(ev);
            }
        }
    }
}
