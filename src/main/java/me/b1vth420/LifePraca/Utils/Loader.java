package me.b1vth420.LifePraca.Utils;

import me.b1vth420.LifePraca.Data.FileManager;
import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Managers.SchematicManager;
import me.b1vth420.LifePraca.Objects.Job;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Loader {

    public static void load(){
        loadScheamatics();
        loadJobs();
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

            HashMap<Material, Integer> moneyGivingBlocks = new HashMap<>();
            HashMap<Material, Integer> moneyGivingDrops  = new HashMap<>();
            HashMap<EntityType, Integer> moneyGivingKills = new HashMap<>();

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
                    System.out.println("AGUABGu3");
                }
            }

            new Job(name, levels, drop, exp, type, moneyGivingBlocks, moneyGivingDrops, moneyGivingKills);
        }
    }
}
