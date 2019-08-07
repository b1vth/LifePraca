package me.b1vth420.LifePraca.Managers;

import me.b1vth420.LifePraca.Objects.BuildingArea;
import me.b1vth420.LifePraca.Objects.PatternArena;
import org.bukkit.Location;

import java.util.concurrent.ConcurrentHashMap;

public class BuildingArenaManager {

    private static ConcurrentHashMap<String, BuildingArea> arenas = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, BuildingArea> getArena(){
        return new ConcurrentHashMap<>(arenas);
    }

    public static void addArena(BuildingArea ba){
        arenas.putIfAbsent(ba.getName(), ba);
    }

    public static void removeArena(BuildingArea ba){
        arenas.remove(ba.getName());
    }

    public static boolean isNear(Location center, int size, int between) {
        for(PatternArena pa : PatternArenaManager.getPatternArenas().values()) {
            double dist = center.distance((pa.getCenter()));
            if(dist < 2*size + between) return true;
        }
        return false;
    }
    public static BuildingArea getEmpty(int level){
        for(BuildingArea ba : getArena().values()){
            if(ba.isEmpty() && ba.getPa().getSchema().getLevel() == level) return ba;
            return null;
        }
        return null;
    }

    public static boolean isIn(Location l) {
        for(BuildingArea r : getArena().values()) {
            if(r.isIn(l)) return true;
        }
        return false;
    }

    public static BuildingArea inWhich(Location l) {
        for(BuildingArea r : getArena().values()) {
            if(r.isIn(l)) return r;
        }
        return null;
    }
}
