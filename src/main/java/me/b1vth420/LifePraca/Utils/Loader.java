package me.b1vth420.LifePraca.Utils;

import me.b1vth420.LifePraca.Data.FileManager;
import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Managers.SchematicManager;
import me.b1vth420.LifePraca.Objects.Job;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Loader {

    public static void load(){
        loadScheamatics();
        loadJobs();
        loadDrugs();
    }

    private static void loadDrugs(){
        ConfigurationSection cs = Main.getInst().getConfig().getConfigurationSection("drugs");
        for (String s : cs.getKeys(false)) {
            ConfigurationSection cs1 = cs.getConfigurationSection(s);

            String name = cs1.getString("item.name");
            Material toBreak = Material.matchMaterial(cs1.getString("item.toBreak").toUpperCase());
            Material m = Material.matchMaterial(cs1.getString("item.type").toUpperCase());
            List<String> lore = new ArrayList<>();
            List<String> commands = new ArrayList<>();
            List<PotionEffect> effects = new ArrayList<>();
            int level = cs1.getInt("level");
            int pkt = cs1.getInt("pkt");

            if(cs1.getStringList("item.lore") != null)
                 lore = cs1.getStringList("item.lore");
            if(cs1.getStringList("commands") != null)
                 commands = cs1.getStringList("commands");
            if(cs1.getStringList("effects") != null)
                for (String e : cs1.getStringList("effects")) {
                    String ss[] = e.split(" ");
                    PotionEffect effect = new PotionEffect(PotionEffectType.getByName(ss[0].toUpperCase()), Integer.parseInt(ss[1])*20, Integer.parseInt(ss[2]) - 1);
                    effects.add(effect);
                }

            ItemStack is = ItemUtil.BuildItem(m, name, lore);

            Main.getInst().getDrugs().put(toBreak, new AbstractMap.SimpleEntry<>(is, new AbstractMap.SimpleEntry<>(commands, new AbstractMap.SimpleEntry<>(effects, new AbstractMap.SimpleEntry<>(level, pkt)))));
        }
    }

    private static void loadScheamatics(){
        for(File f : FileManager.getSchematicsDir().listFiles()){
            if(f != null) {
                try {
                    SchematicUtils.loadSchematic(f);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Bukkit.getLogger().info("[LifePrace] Zaladowano " + SchematicManager.getSchematics().size() + " schematow");
    }

    private static void loadJobs() {
        ConfigurationSection cs = FileManager.getJobs().getConfigurationSection("jobs");
        for (String s : cs.getKeys(false)) {
            ConfigurationSection cs1 = cs.getConfigurationSection(s);

            String name = cs1.getString("name");
            Map<Integer, Double> levels = new HashMap<>();
            for(String sx : cs1.getStringList("levels")){
                String[] ss = sx.split(" ");
                levels.putIfAbsent(Integer.parseInt(ss[0]), Double.parseDouble(ss[1]));
            }
            boolean drop = cs1.getBoolean("drop");
            boolean exp = cs1.getBoolean("exp");
            jobType type = jobType.valueOf(cs1.getString("jobType").toUpperCase());
            ItemStack jobItem = new ItemStack(Material.matchMaterial(cs1.getString("jobItem").toUpperCase()));
            List<String> jobDescription = cs1.getStringList("jobDescription");

            HashMap<Material, Integer> moneyGivingBlocks = new HashMap<>();
            HashMap<Material, Integer> moneyGivingDrops  = new HashMap<>();
            HashMap<EntityType, Integer> moneyGivingKills = new HashMap<>();
            List<List<ItemStack>> prizes = new ArrayList<>();

            if(cs1.contains("moneyGivingBlocks")) {
                for (String sx : cs1.getStringList("moneyGivingBlocks")) {
                    String[] ss = sx.split(" ");
                    moneyGivingBlocks.put(Material.matchMaterial(ss[0].toUpperCase()), Integer.parseInt(ss[1]));
                }
            }
            if(cs1.getStringList("moneyGivingDrops") != null ) {
                for (String sx : cs1.getStringList("moneyGivingDrops")) {
                    String[] ss = sx.split(" ");
                    moneyGivingDrops.put(Material.matchMaterial(ss[0].toUpperCase()), Integer.parseInt(ss[1]));
                }
            }
            if(cs1.contains("moneyGivingKills")){
                for(String sx : cs1.getStringList("moneyGivingKills")){
                    String[] ss = sx.split(" ");
                    moneyGivingKills.put(EntityType.valueOf(ss[0].toUpperCase()), Integer.parseInt(ss[1]));
                }
            }
            if(cs1.contains("prizes")){
                for(int i=0;i<cs1.getStringList("prizes").size(); i++)
                    prizes.add(ItemUtil.parseItemsFromString(cs1.getStringList("prizes").get(i)));
            }

            new Job(name, levels, drop, exp, type, moneyGivingBlocks, moneyGivingDrops, moneyGivingKills, jobItem, jobDescription, prizes);
        }
    }
}
