package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class PracaCommand extends Command{

    public PracaCommand() {
        super("praca", null, "LifePraca.Praca", "/praca <nazwa>", true, 1, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        JobUser ju = JobUser.get(p);
        ju.setJob(Job.get(args[0]));
        p.sendMessage(ChatUtil.chat(ju.getJob().getName()));
        for(Map.Entry<Material, Integer> map : ju.getJob().getMoneyGivingBlocks().entrySet()){
            p.sendMessage(map.getKey().toString());
        }
    }
}
