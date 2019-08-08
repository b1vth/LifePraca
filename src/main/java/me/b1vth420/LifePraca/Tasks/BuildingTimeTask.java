package me.b1vth420.LifePraca.Tasks;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Managers.JobUserManager;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.SchematicUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BuildingTimeTask extends BukkitRunnable {

    @Override
    public void run() {
        for(JobUser ju : JobUserManager.getJobUsers().values()){
            if(ju.hasJob() && ju.isBuilding()){
                Player p = Bukkit.getPlayer(ju.getUuid());
                if(p.getLevel() > 0){
                    p.setLevel(p.getLevel() -1);
                } else {
                    p.sendTitle(ChatUtil.chat("&2Prace"), ChatUtil.chat(Lang.getInst().buildingCheckInProgressMessage), 20, 60, 20);
                    ju.setBuilding(false);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
                        public void run() {
                            SchematicUtils.check(ju.getBuildingArea().getPa(), ju.getBuildingArea(), p);
                        }
                    }, 200L);
                }
            }
        }
    }
}
