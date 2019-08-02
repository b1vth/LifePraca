package me.b1vth420.LifePraca.Data;

import me.b1vth420.LifePraca.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileManager {
    private static YamlConfiguration jobs, lang;

    private static File mainDir = Main.getInst().getDataFolder();
    private static File cfgFile = new File(mainDir, "config.yml");
    private static File jobsFile = new File(mainDir, "jobs.yml");
    private static File langFile = new File(mainDir, "lang.yml");
    private static File schematicsDir = new File(mainDir, "schematics");

    public static void check(){
        if(!(mainDir.exists())) mainDir.mkdir();
        if(!(cfgFile.exists())) Main.getInst().saveDefaultConfig();
        if(!(jobsFile.exists())) Main.getInst().saveResource("jobs.yml", true);
        if(!(langFile.exists())) Main.getInst().saveResource("lang.yml", true);
        if(!(schematicsDir.exists())) schematicsDir.mkdir();

        jobs = YamlConfiguration.loadConfiguration(jobsFile);
        lang = YamlConfiguration.loadConfiguration(langFile);
    }

    public static File getMainDir() {
        return mainDir;
    }
    public static YamlConfiguration getLang()  { return lang; }
    public static YamlConfiguration getJobs(){
        return jobs;
    }
    public static File getSchematicsDir() { return schematicsDir; }
}
