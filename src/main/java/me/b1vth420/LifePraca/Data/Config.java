package me.b1vth420.LifePraca.Data;

import me.b1vth420.LifePraca.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private static Config inst;
    private FileConfiguration cfg = Main.getInst().getConfig();

    private Config(){
        inst = this;
    }

    public String mysqlIP;
    public int mysqlPort;
    public String mysqlDatabase;
    public String mysqlUsername;
    public String mysqlPassword;
    public boolean mysqlSafeSave;

    public int benefit;
    public int benefitTime;
    public List<String> badgeLore;
    public Map<Integer, String> badgeLevels = new HashMap<>();
    public int deathTime;

    public String sucessfullCreatePattern;
    public String sucessfullCreateBuilding;
    public String moneyAddMessage;

    public void load(){
        mysqlIP = cfg.getString("MySQL.ip");
        mysqlPort = cfg.getInt("MySQL.port");
        mysqlDatabase = cfg.getString("MySQL.database");
        mysqlUsername = cfg.getString("MySQL.username");
        mysqlPassword = cfg.getString("MySQL.password");
        mysqlSafeSave = cfg.getBoolean("MySQL.safeSave");

        benefit = cfg.getInt("unemployed.benefit");
        benefitTime = cfg.getInt("unemployed.time");
        badgeLore = cfg.getStringList("badge.lore");
        for(String s : cfg.getStringList("badge.levelsName")){
            String[] ss = s.split(" ");
            badgeLevels.putIfAbsent(Integer.parseInt(ss[0]), ss[1]);
        }
        deathTime = cfg.getInt("death.time");

        sucessfullCreatePattern = cfg.getString("messages.sucessfullCreatePattern");
        sucessfullCreateBuilding = cfg.getString("messages.sucessfullCreateBuilding");
        moneyAddMessage = cfg.getString("messages.moneyAddMessage");
    }

    public static Config getInst(){
        if (inst == null) {
            return new Config();
        }
        return inst;
    }
}
