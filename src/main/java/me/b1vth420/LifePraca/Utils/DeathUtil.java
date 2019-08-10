package me.b1vth420.LifePraca.Utils;


import me.b1vth420.LifePraca.Enums.jobType;
import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Managers.JobUserManager;
import me.b1vth420.LifePraca.Objects.JobUser;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class DeathUtil {

    public static HashMap<String, Player> toSteal = new HashMap<>();

    public static void addDeath(Player p){
        Main.getInst().getDead().putIfAbsent(p.getName(), p);
        for(JobUser ju : JobUserManager.getJobUsers().values())
            if(!(ju.getJob().getJobType().equals(jobType.PREMIUM)))
                ju.getPlayer().hidePlayer(p);
    }

    public static void removeDeath(Player p){
        Main.getInst().getDead().remove(p.getName());
    }

    public static boolean isDead(Player px){
        for(Player p : Main.getInst().getDead().values()){
            if(p.equals(px)) return true;
        }
        return false;
    }

    public static boolean containsSteel(Player p){
        for(Player px : toSteal.values()){
            if(px.equals(p)) return true;
        }
        return false;
    }
}
