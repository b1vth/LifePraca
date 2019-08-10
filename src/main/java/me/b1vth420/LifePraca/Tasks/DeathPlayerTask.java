package me.b1vth420.LifePraca.Tasks;

import me.b1vth420.LifePraca.Utils.DeathUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathPlayerTask extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            if(DeathUtil.isDead(p)){
                p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, (Object) Material.REDSTONE_BLOCK);
                Location l = p.getLocation().add(0, 0.5, 0);
                p.getWorld().playEffect(l, Effect.STEP_SOUND, (Object) Material.REDSTONE_BLOCK);
            }
        }
    }
}
