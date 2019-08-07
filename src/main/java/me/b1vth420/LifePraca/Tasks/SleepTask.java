package me.b1vth420.LifePraca.Tasks;

import me.b1vth420.LifePraca.Utils.DeathUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;

public class SleepTask extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : DeathUtil.sleep.values()){
            DeathUtil.playSleepAnimation(p);
        }
    }
}
