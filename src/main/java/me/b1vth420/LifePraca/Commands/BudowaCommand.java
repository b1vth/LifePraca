package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Gui.BudowaGui;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Managers.BuildingArenaManager;
import me.b1vth420.LifePraca.Objects.BuildingArea;
import me.b1vth420.LifePraca.Objects.Job;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Objects.PatternArena;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import me.b1vth420.LifePraca.Utils.InventoryUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BudowaCommand extends Command{
    public BudowaCommand() {
        super("budowa", null, "LifePraca.budowa", "/budowa", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        JobUser ju = JobUser.get(p);
        if(ju.hasJob() && ju.getJob().equals(Job.get("budowniczy"))) BudowaGui.budowa(p);

    }
}
