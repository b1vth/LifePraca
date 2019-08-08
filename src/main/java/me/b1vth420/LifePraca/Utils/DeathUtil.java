package me.b1vth420.LifePraca.Utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import me.b1vth420.LifePraca.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class DeathUtil {

    public static HashMap<String, Player> sleep = new HashMap<>();
    public static HashMap<String, Player> toSteal = new HashMap<>();

    public static void playSleepAnimation(Player asleep) {
        final PacketContainer bedPacket = Main.getInst().getManager().createPacket(PacketType.Play.Server.BED, false);
        final Location loc = asleep.getLocation();

        bedPacket.getEntityModifier(asleep.getWorld()).
                write(0, asleep);
        bedPacket.getBlockPositionModifier().write(0, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));

        broadcastNearby(asleep, bedPacket);
        sleep.putIfAbsent(asleep.getName(), asleep);
    }

    public static boolean isSleeping(Player px){
        for(Player p : sleep.values()){
            if(p.equals(px)) return true;
        }
        return false;
    }

    public static void stopSleepAnimation(Player sleeping) {
        final PacketContainer animation = Main.getInst().getManager().createPacket(PacketType.Play.Server.ANIMATION, false);

        // http://wiki.vg/Protocol#Animation
        animation.getEntityModifier(sleeping.getWorld()).
                write(0, sleeping);
        animation.getIntegers().
                write(1, 2);

        broadcastNearby(sleeping, animation);
        sleep.remove(sleeping.getName());
    }

    private static void broadcastNearby(Player asleep, PacketContainer bedPacket) {
        for (Player observer : Bukkit.getOnlinePlayers()) {
            if(observer != asleep) {
                try {
                    Main.getInst().getManager().sendServerPacket(observer, bedPacket);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("Cannot send packet.", e);
                }
            }
        }
    }

    public static boolean containsSteel(Player p){
        for(Player px : toSteal.values()){
            if(px.equals(p)) return true;
        }
        return false;
    }
}
