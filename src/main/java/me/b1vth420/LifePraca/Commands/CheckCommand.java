package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Data.Lang;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.BuildingArea;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Objects.PatternArena;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.SchematicUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCommand extends Command{

    public CheckCommand() {
        super("check", null, null, "/check", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;


        JobUser ju = JobUser.get(p);


        BuildingArea ba = ju.getBuildingArea();
        PatternArena pa = ba.getPa();

        if (ju.isBuilding()) {
            ju.setBuilding(false);
            p.sendTitle(ChatUtil.chat("&2Prace"), ChatUtil.chat(Lang.getInst().buildingCheckStartedMessage), 20, 60, 20);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
                public void run() {
                    SchematicUtils.check(pa, ba, p);
                }
            }, 200L);
        }
    }
}
