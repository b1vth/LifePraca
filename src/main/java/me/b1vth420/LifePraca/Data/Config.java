package me.b1vth420.LifePraca.Data;

import me.b1vth420.LifePraca.Main;
import org.bukkit.configuration.file.FileConfiguration;

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
