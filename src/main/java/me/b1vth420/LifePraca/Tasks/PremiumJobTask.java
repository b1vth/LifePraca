package me.b1vth420.LifePraca.Tasks;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Managers.JobUserManager;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import org.bukkit.scheduler.BukkitRunnable;

public class PremiumJobTask extends BukkitRunnable {
    @Override
    public void run() {
        for(JobUser ju : JobUserManager.getJobUsers().values()){
            if(ju.hasJob() && ju.getJob().getJobType().equals(jobType.PREMIUM) && ju.isOnDuty()){
                Job j = ju.getJob();
                ju.setMoney(ju.getMoney() + j.getLevels().get(ju.getLevels().get(j).getKey()));
                ju.sendMessage(Lang.getInst().premiumJobRewardMessage.replace("{MONEY}", String.valueOf(j.getLevels().get(ju.getLevels().get(j).getKey()))));
            }
        }
    }
}
