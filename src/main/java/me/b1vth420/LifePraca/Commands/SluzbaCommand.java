package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SluzbaCommand extends Command{
    public SluzbaCommand() {
        super("sluzba", null, "LifePrace.Sluzba", "/sluzba", true, 0, new String[]{"duty"});
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        JobUser ju = JobUser.get(p);
        if(ju.hasJob() && ju.getJob().getJobType().equals(jobType.PREMIUM)){
            if(ju.isOnDuty()){
                ju.setOnDuty(false);
                p.sendTitle(ChatUtil.chat("&2Praca"), ChatUtil.chat(Lang.getInst().dutyLeaveMessage), 20, 60, 20);
            } else {
                ju.setOnDuty(true);
                p.sendTitle(ChatUtil.chat("&2Praca"), ChatUtil.chat(Lang.getInst().dutyJoinMessage), 20, 60, 20);
            }
        }
    }
}
