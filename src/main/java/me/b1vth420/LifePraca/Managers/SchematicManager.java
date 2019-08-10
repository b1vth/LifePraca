package me.b1vth420.LifePraca.Managers;

import me.b1vth420.LifePraca.Objects.Schematic;

import java.util.HashSet;
import java.util.Random;

public class SchematicManager {

    private static HashSet<Schematic> schematics = new HashSet<>();

    public static HashSet<Schematic> getSchematics() {
        return new HashSet<>(schematics);
    }

    public static void addSchematic(Schematic s) {
        schematics.add(s);
    }

    public static void removeSchematic(Schematic s) {
        schematics.remove(s);
    }

    public static Schematic getRandom(int level) {
        int i = 0;
        int random = new Random().nextInt(schematics.size());
        for (Schematic s : SchematicManager.getSchematics()) {
            if (i == random && s.getLevel() == level)
                return s;
            i++;
        }
        return null;
    }
}
