package me.b1vth420.LifePraca.Listeners.Job;

import me.b1vth420.LifePraca.Listeners.Events.LevelUpEvent;
import me.b1vth420.LifePraca.Objects.JobUser;
import me.b1vth420.LifePraca.Utils.ChatUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.AbstractMap;

public class LevelUpListener implements Listener {

    @EventHandler
    public void LevelUp(LevelUpEvent e){
        JobUser ju = JobUser.get(e.getPlayer());
        //BlocksUtil.spawnFireworks(e.getPlayer().getLocation(), 3);
        e.getPlayer().sendMessage(ChatUtil.chat("&7Gratulacje awansowales na poziom &c" + e.getLevel() +1));
        ju.getLevels().put(ju.getJob(), new AbstractMap.SimpleEntry<>(ju.getLevels().get(ju.getJob()).getKey() +1, ju.getLevels().get(ju.getJob()).getValue()));
    }
}
