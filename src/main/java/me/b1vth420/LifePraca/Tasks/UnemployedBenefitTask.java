package me.b1vth420.LifePraca.Tasks;

import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Managers.JobUserManager;
import me.b1vth420.LifePraca.Objects.JobUser;
import org.bukkit.scheduler.BukkitRunnable;

public class UnemployedBenefitTask extends BukkitRunnable {
    @Override
    public void run() {
        for (JobUser ju : JobUserManager.getJobUsers().values())
            if (!ju.hasJob() && ju.getJob() == null && ju != null) {
                ju.setMoney(ju.getMoney() + Config.getInst().benefit);
                ju.sendActionBar("&aOtrzymales zasilek " + Config.getInst().benefit);
            }
    }
}
