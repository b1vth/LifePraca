package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Objects.PatternArena;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreatePatternCommand extends Command{
    public CreatePatternCommand() {
        super("createpattern", null, "LifePraca.CreatePattern", "/createpattern <nazwa> <wielkosc>", true, 2, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        PatternArena pa =  new PatternArena(args[0], p.getLocation(), Integer.parseInt(args[1])+1);
        p.sendMessage(ChatUtil.chat(Config.getInst().sucessfullCreatePattern.replace("{PATTERNNAME}", pa.getName())));
    }
}
