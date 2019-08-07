package me.b1vth420.LifePraca.Managers;

import me.b1vth420.LifePraca.Objects.JobUser;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class JobUserManager {

    private static ConcurrentHashMap<String, JobUser> jobusers = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Player> freezedPlayers = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, JobUser> getJobUsers(){
        return new ConcurrentHashMap<>(jobusers);
    }

    public static void addJobUser(JobUser ju){
        jobusers.putIfAbsent(ju.getName(), ju);
    }

    public static void removeJobUser(JobUser ju){
        jobusers.remove(ju.getName());
    }

    public static ConcurrentHashMap<String, Player> getFreezedPlayers() { return new ConcurrentHashMap<>(freezedPlayers); }

    public static void addFreeze(Player p){ freezedPlayers.putIfAbsent(p.getName(), p); }

    public static void removeFreeze(Player p){
        freezedPlayers.remove(p.getName());
    }

    public static boolean isFreezed(Player p) {
        for(Player px : freezedPlayers.values()){
            if(px.equals(p)) return true;
        }
        return false;
    }

}
