package me.b1vth420.LifePraca.Commands;


import me.b1vth420.LifePraca.Main;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DrugsCommand extends Command{

    public DrugsCommand() {
        super("drugs", null, "LifePraca.Drugs", "/drugs", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        for(Entry<Material, Map.Entry<ItemStack, Entry<List<String>, Entry<List<PotionEffect>, Map.Entry<Integer, Integer>>>>> entry : Main.getInst().getDrugs().entrySet()) {
            p.getInventory().addItem(entry.getValue().getKey());
        }
    }
}
