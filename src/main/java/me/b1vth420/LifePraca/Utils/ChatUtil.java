package me.b1vth420.LifePraca.Utils;

import me.b1vth420.LifePraca.Main;
import me.b1vth420.LifePraca.Objects.JobUser;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class ChatUtil {

    public static String chat(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    public static void sendActionBar(Player p, String message){
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(chat(message)).create());
    }
    public static void broadcastActionBar(String message){
        for(Player p : Bukkit.getOnlinePlayers())
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(chat(message)).create());
    }
    public static String formatDouble(double d){
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(d);
    }
    public static String fixJobsLore(String s, String name, int id, String level, JobUser ju){
        return s.replace("{ID}", "#00" + id)
                .replace("{NAME}", name)
                .replace("{TIME}", DataUtil.getDate(ju.getJoinTime()))
                .replace("{LEVEL}", level);
    }
}
