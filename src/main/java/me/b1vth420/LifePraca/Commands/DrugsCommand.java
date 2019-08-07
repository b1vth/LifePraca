package me.b1vth420.LifePraca.Commands;


import me.b1vth420.LifePraca.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

public class DrugsCommand extends Command{

    public DrugsCommand() {
        super("drugs", null, null, "/drugs", true, 0, new String[0]);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        for(Entry<ItemStack, Entry<List<String>, List<PotionEffect>>> entry : Main.getInst().getDrugs().entrySet()) {
            p.getInventory().addItem(entry.getKey());
            for(String s : entry.getValue().getKey()){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
            }
            for(PotionEffect pe : entry.getValue().getValue()){
                p.addPotionEffect(pe);
            }
        }
    }
}
