package me.b1vth420.LifePraca.Managers;

import me.b1vth420.LifePraca.Objects.PatternArena;
import org.bukkit.Location;

import java.util.concurrent.ConcurrentHashMap;

public class PatternArenaManager {

    private static ConcurrentHashMap<String, PatternArena> arenas = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, PatternArena> getPatternArenas(){
        return new ConcurrentHashMap<>(arenas);
    }

    public static void addPatternArena(PatternArena pa){
        arenas.putIfAbsent(pa.getName(), pa);
    }

    public static void removePatternArena(PatternArena pa){
        arenas.remove(pa.getName());
    }

    public static boolean isIn(Location l) {
        for(PatternArena r : getPatternArenas().values()) {
            if(r.isIn(l)) return true;
        }
        return false;
    }

    public static PatternArena inWhich(Location l) {
        for(PatternArena r : getPatternArenas().values()) {
            if(r.isIn(l)) return r;
        }
        return null;
    }
}
