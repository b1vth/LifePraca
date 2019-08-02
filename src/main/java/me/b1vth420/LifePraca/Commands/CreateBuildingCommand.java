package me.b1vth420.LifePraca.Commands;

import me.b1vth420.LifePraca.Data.Config;
import me.b1vth420.LifePraca.Managers.BuildingArenaManager;
import me.b1vth420.LifePraca.Objects.BuildingArea;
import me.b1vth420.LifePraca.Objects.PatternArena;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateBuildingCommand extends Command{
    public CreateBuildingCommand() {
        super("createbuilding", null, "LifePraca.CreateBuilding", "/createbuilding <nazwa> <nazwa wzoru>", true, 1, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        PatternArena pa = PatternArena.get(args[1]);
        if(pa == null){
            p.sendMessage(ChatUtil.chat("&4Blad! &cWzor o nazwie &4" + args[1] + "&c nie istnieje!"));
            return;
        }
        if(BuildingArenaManager.isNear(p.getLocation(), pa.getSize(), 5)){
            p.sendMessage(ChatUtil.chat("&4Blad! &cZnajdujesz sie za blisko areny ze wzorem!"));
            return;
        }
        BuildingArea ba = new BuildingArea(args[0], p.getLocation(), pa);
        p.sendMessage(ChatUtil.chat(Config.getInst().sucessfullCreateBuilding).replace("{NAME}", ba.getName()).replace("{PATTERNNAME}", pa.getName()));
    }
}
