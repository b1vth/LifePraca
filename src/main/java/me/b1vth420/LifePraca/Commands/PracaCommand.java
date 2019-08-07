package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Gui.PracaGui;
import me.b1vth420.LifePraca.Listeners.Events.JobJoinEvent;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class PracaCommand extends Command{

    public PracaCommand() {
        super("praca", null, "LifePraca.Praca", "/praca <nazwa>", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        if(args.length > 0 && p.hasPermission("LifePraca.admin")){
            Job j = Job.get(args[1]);
            if(Bukkit.getPlayer(args[0]) == null){
                p.sendMessage(ChatUtil.chat("&4Blad! &cTakiego gracza nie ma na serwerze!"));
                return;
            }
            JobUser ju = JobUser.get(Bukkit.getPlayer(args[0]));

            if(j == null){
                p.sendMessage(ChatUtil.chat("&4Blad! &cTakiej pracy nie ma na serwerze!"));
                return;
            }
            ju.setJob(j);
            Bukkit.getPlayer(args[0]).sendMessage(ChatUtil.chat("&aAdministrator " + p.getName() + " ustawil ci prace " + j.getName()));
            p.sendMessage(ChatUtil.chat("&7Zmieniles prace graczowi&c " + ju.getName() + " &7na&c " + j.getName()));

            JobJoinEvent eve = new JobJoinEvent(Bukkit.getPlayer(args[0]));
            Bukkit.getPluginManager().callEvent(eve);
        } else
            PracaGui.jobsTypes(p);
    }
}
