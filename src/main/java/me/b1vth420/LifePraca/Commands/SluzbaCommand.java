package me.b1vth420.LifePraca.Commands;

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
                p.sendMessage(ChatUtil.chat("&7Opusciles sluzbe"));
            } else {
                ju.setOnDuty(true);
                p.sendMessage(ChatUtil.chat("&7Dolaczyles do sluzby"));
            }
        }
    }
}
