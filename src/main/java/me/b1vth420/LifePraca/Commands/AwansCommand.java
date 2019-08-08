package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Listeners.Events.JobJoinEvent;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.AbstractMap;

public class AwansCommand extends Command{
    public AwansCommand() {
        super("awans", null, "LifePraca.Awans", "/awans nick", false, 1, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Player p2 = Bukkit.getPlayer(args[0]);
        if(p2 == null) { p.sendMessage(ChatUtil.chat("&4Blad &cNie ma takiego gracza na serwerze!")); return; }
        JobUser ju = JobUser.get(p2);
        ju.getLevels().replace(ju.getJob(), new AbstractMap.SimpleEntry<>(ju.getLevels().get(ju.getJob()).getKey()+1, ju.getLevels().get(ju.getJob()).getValue()));
        ju.sendMessage(ChatUtil.chat(Lang.getInst().promotionMessage.replace("{PLAYER}", sender.getName())));
        p.sendMessage(ChatUtil.chat("&aAwansowales gracza " + ju.getName()));
        Main.getInst().getSQLManager().updateLog(JobUser.get(p), "AWANS", "Gracz " + ju.getName() + " zostal awansowany na " + Config.getInst().badgeLevels.get(ju.getLevels().get(ju.getJob()).getKey()));

        JobJoinEvent e = new JobJoinEvent(p);
        Bukkit.getPluginManager().callEvent(e);
    }
}
