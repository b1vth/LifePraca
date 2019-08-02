package me.b1vth420.LifePraca.Commands;

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
        if(ju.hasJob()) {
            if (ju.getJob().equals(Job.get("budowniczy"))) {

                teleportToWork(p, ju);
            }
        }
    }
    private void teleportToWork(Player p, JobUser ju){
        BuildingArea ba = BuildingArenaManager.getEmpty();
        PatternArena pa = ba.getPa();
        ju.setBuildingArea(ba);
        p.teleport(pa.getCenter());
        p.sendTitle(ChatUtil.chat("&2Praca"), ChatUtil.chat("&aZostales przeteleportowany na budowe"), 20, 60, 20);
        ba.setEmpty(false);
        pa.fill();
        InventoryUtil.storeAndClearInventory(p);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
            public void run() {
                ju.setBuilding(true);
                for(Material m : pa.getBlocks()){
                    p.getInventory().addItem(new ItemStack(m, 20));
                }
                p.teleport(ba.getCenter());
                p.setLevel(pa.getSchema().getTime());
            }
        }, 200L);
    }
}
